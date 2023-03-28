package com.example.newsapp.restapi

import com.example.newsapp.modules.NewsResponse


class DataSource {

    suspend fun loadNews() : NewsResponse {
        return RetrofitHelper.getInstance().create(NewsApiService::class.java).getNews();
    }
}