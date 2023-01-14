package com.codeplayon.framework.mvvm.data.api

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("api")
    suspend fun getUsers(): Response<String>

}