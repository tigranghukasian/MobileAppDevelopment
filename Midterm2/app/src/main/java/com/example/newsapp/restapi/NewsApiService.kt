package com.example.newsapp.restapi

import com.example.newsapp.modules.Response
import com.example.newsapp.modules.Student
import retrofit2.http.GET

interface NewsApiService {

    @GET("/api/?inc=nat,name,email&results=100")
    suspend fun getStudents(): Response


}