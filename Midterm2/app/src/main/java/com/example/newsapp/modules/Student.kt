package com.example.newsapp.modules

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("results")
    val student: List<Student>,
)
data class Student (
    @SerializedName("name")
    val name: Name
)
data class Name (
    @SerializedName("title")
    val title: String?,
    @SerializedName("first")
    val first: String?,
    @SerializedName("last")
    val last: String?
)