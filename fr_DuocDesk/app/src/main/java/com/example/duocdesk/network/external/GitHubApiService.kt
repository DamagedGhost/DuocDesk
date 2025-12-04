package com.example.duocdesk.network.external

import com.example.duocdesk.network.GitRepo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface GitHubApiService {

    @GET("user/repos")
    fun getMyRepos(
        @Header("Authorization") authHeader: String
    ): Call<List<GitRepo>>
}