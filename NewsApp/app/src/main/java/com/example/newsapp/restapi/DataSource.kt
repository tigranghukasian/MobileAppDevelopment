package com.example.newsapp.restapi

import com.example.newsapp.modules.NewsResponse


class DataSource {

    suspend fun fetchNews(country: String, category: String, searchValue: String) : NewsResponse {
        return RetrofitHelper.getInstance().create(NewsApiService::class.java).getNews(country, category, searchValue);
    }
}