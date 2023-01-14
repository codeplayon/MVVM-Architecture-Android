package com.codeplayon.framework.mvvm.data.api

import retrofit2.Response

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override suspend fun getUsers(): Response<String> = apiService.getUsers()

}