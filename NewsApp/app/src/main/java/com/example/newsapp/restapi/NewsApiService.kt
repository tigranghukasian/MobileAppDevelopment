package com.example.newsapp.restapi

import com.example.newsapp.modules.NewsResponse
import retrofit2.http.GET

interface NewsApiService {

    @GET("/v2/top-headlines?country=us&apiKey=40bcb86b58dc4994a6eda6f8b6433d85")
    suspend fun getNews(): NewsResponse


}