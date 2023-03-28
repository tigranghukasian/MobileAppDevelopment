package com.example.newsapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.modules.Response
import com.example.newsapp.modules.Student
import com.example.newsapp.restapi.DataSource
import kotlinx.coroutines.launch

class DataLoaderViewModel : ViewModel() {

    private val _studentResponse = MutableLiveData<Result<Response>>();
    var studentResponse: LiveData<Result<Response>> = _studentResponse;

    fun getArticles() {
        viewModelScope.launch {
            try {
                val response = DataSource().loadStudents()
                _studentResponse.postValue(Result.success(response))
            } catch (e: Exception) {
                _studentResponse.postValue(Result.error(e))
            }
        }
    }
}