package com.example.newsapp

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

    fun getArticles() {
        viewModelScope.launch {
            try {
                val response = DataSource().loadNews()
                _newsResponse.postValue(Result.success(response))
            } catch (e: Exception) {
                _newsResponse.postValue(Result.error(e))
            }
        }
    }
}