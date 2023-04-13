package com.example.newsapp.modules

import com.google.gson.annotations.SerializedName

data class NewsResponse (
    @SerializedName("articles")
    val articles: List<Articles>
)
data class Articles (
    @SerializedName("title")
    val title: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("urlToImage")
    val  imageUrl : String?
)