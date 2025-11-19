package com.example.duocdesk.network

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.Call

interface GitHubApiService {

    @GET("user/repos")
    fun getMyRepos(
        @Header("Authorization") authHeader: String
    ): Call<List<GitRepo>>
}
