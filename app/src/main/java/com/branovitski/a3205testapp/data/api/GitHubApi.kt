package com.branovitski.a3205testapp.data.api

import com.branovitski.a3205testapp.domain.model.Repository
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {
    @GET("/users/{username}/repos")
    suspend fun getSearchRepositories(
        @Path("username") query: String,
        @Query("per_page") perPage: Int = 100
    ): List<Repository>

    @GET("/repos/{owner}/{repo}/zipball")
    suspend fun downloadRepository(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
    ): ResponseBody
}