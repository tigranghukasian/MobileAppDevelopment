package com.example.newsapp.modules

import android.os.Parcel
import android.os.Parcelable
import androidx.versionedparcelable.ParcelField
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NewsResponse (
    @SerializedName("articles")
    val articles: List<Article>
)
data class Article(
    @SerializedName("title")
    val title: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("urlToImage")
    val  imageUrl : String?,
    @SerializedName("author")
    val author : String?,
    @SerializedName("source")
    val source : Source?

) : Serializable
data class Source (
    @SerializedName("name")
    val name: String?
) :Serializable