package com.github.demo.data.remote

import com.github.demo.model.RepositoryModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search/repositories?sort=stars")
    suspend fun getTrendingRepositories(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int,
        @Query("sort") sort: String = "stars",
        @Query("order") order: String = "desc"
    ): Response<RepositoryModel>
}