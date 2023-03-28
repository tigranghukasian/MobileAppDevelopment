package com.example.newsapp.restapi

import com.example.newsapp.modules.Response
import com.example.newsapp.modules.Student


class DataSource {

    suspend fun loadStudents() : Response {
        return RetrofitHelper.getInstance().create(NewsApiService::class.java).getStudents();
    }
}