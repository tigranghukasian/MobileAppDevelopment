package com.example.newsapp.restapi

import com.example.newsapp.modules.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("/v2/top-headlines")
    suspend fun getNews(@Query("country") country: String, @Query("apiKey") apiKey: String = "685bda74ad7846998269c55ff27bf2dc"): NewsResponse


}