package com.example.newsapp.restapi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    val newsUrl = "https://newsapi.org/"
    val baseUrl = "https://jsonplaceholder.typicode.com/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(newsUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
