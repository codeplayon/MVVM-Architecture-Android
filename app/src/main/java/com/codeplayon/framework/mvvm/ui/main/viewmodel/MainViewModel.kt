package com.codeplayon.framework.mvvm.ui.main.viewmodel

import androidx.lifecycle.*
import com.codeplayon.framework.mvvm.data.repository.MainRepository
import com.codeplayon.framework.mvvm.utils.NetworkHelper
import com.codeplayon.framework.mvvm.utils.Resource
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _users = MutableLiveData<Resource<String>>()
    val users: LiveData<Resource<String>>
        get() = _users

    init {
        val timer = Timer()
        val hourlyTask: TimerTask = object : TimerTask() {
            override fun run() {
                fetchUsers()
                // your code here...
            }
        }
        timer.schedule(hourlyTask, 0L, 1000 *10 )

    }

    private fun fetchUsers() {
        viewModelScope.launch {
            _users.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.getUsers().let {
                    if (it.isSuccessful) {
                        _users.postValue(Resource.success(it.body()))
                    } else _users.postValue(Resource.error(it.errorBody().toString(), null))
                }
            } else _users.postValue(Resource.error("No internet connection", null))
        }
    }
}