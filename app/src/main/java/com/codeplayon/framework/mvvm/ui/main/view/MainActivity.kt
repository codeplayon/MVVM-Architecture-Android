package com.codeplayon.framework.mvvm.ui.main.view

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.codeplayon.framework.mvvm.R
import com.codeplayon.framework.mvvm.ui.main.adapter.MainAdapter
import com.codeplayon.framework.mvvm.ui.main.viewmodel.MainViewModel
import com.codeplayon.framework.mvvm.utils.Status
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.reflect.Type
import java.util.*

class MainActivity : AppCompatActivity() {

    private val mainViewModel : MainViewModel by viewModel()
    private lateinit var adapter: MainAdapter
    lateinit var date : ArrayList<String>
    var replace : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        date = ArrayList()
        if (getArrayList("date").isEmpty()){

        }else{
            date  = getArrayList("date")

        }
        setupObserver()
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(date)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter

        // Setup and Handover data to recyclerview

    }


    private fun setupObserver() {
        mainViewModel.users.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.let { users -> renderList(users) }
                    recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(users: String) {
        if (date.size==10){
          date.set(replace,users);
            replace = replace+1;
            if (replace==10){
                replace = 0
            }
        }else{
            date.add(users)
        }
        setupUI()
    }

    override fun onPause() {
        super.onPause()
        saveArrayList(date,"date")
    }


    fun saveArrayList(list: ArrayList<String>?, key: String?) {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor: SharedPreferences.Editor = prefs.edit()
        val gson = Gson()
        val json: String = gson.toJson(list)
        editor.putString(key, json)
        editor.apply()
    }

    fun getArrayList(key: String): ArrayList<String> {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val gson = Gson()
        val json: String? = prefs.getString(key,"[]")
        val type: Type = object : TypeToken<ArrayList<String?>?>() {}.getType()
        return gson.fromJson(json, type)
    }
}
