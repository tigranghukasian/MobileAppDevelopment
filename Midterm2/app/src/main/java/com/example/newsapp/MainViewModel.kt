package com.example.newsapp

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

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