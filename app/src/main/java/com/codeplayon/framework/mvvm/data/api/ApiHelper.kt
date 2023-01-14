package com.codeplayon.framework.mvvm.data.api

import retrofit2.Response

interface ApiHelper {

    suspend fun getUsers(): Response<String>
}