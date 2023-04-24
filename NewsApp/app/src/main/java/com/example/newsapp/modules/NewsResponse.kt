package com.example.newsapp.modules

import com.google.gson.annotations.SerializedName

data class NewsResponse (
    @SerializedName("articles")
    val articles: List<Article>
)
data class Article (
    @SerializedName("title")
    val title: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("urlToImage")
    val  imageUrl : String?
)