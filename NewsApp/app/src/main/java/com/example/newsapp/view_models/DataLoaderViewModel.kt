package com.example.newsapp.com.example.newsapp.view_models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.modules.NewsResponse
import com.example.newsapp.restapi.DataSource
import kotlinx.coroutines.launch

class DataLoaderViewModel : ViewModel() {

    private val _newsResponse = MutableLiveData<Result<NewsResponse>>();
    var newsResponse: LiveData<Result<NewsResponse>> = _newsResponse;

    private val _searchCategory = mutableStateOf("")
    val searchCategory: State<String> = _searchCategory


    fun getArticles(category: String, searchValue: String = "") {
        viewModelScope.launch {
            try {
                val response = DataSource().fetchNews("US", category, searchValue)
                _newsResponse.postValue(Result.success(response))
            } catch (e: Exception) {8
                _newsResponse.postValue(Result.error(e))
            }
        }
    }
}
sealed class Result<out T : Any> {
    data class Loading(val message:String ="") : Result<Nothing>()
    data class Success<out T: Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    companion object {
        fun <T : Any> loading(message: String = ""): Result<T> = Loading(message)
        fun <T : Any> success(data: T): Result<T> = Success(data)
        fun error(exception: Exception): Result<Nothing> = Error(exception)
    }
}