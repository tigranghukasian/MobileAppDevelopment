package com.example.newsapp.view_models

import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsapp.com.example.newsapp.view_models.DataLoaderViewModel
import com.example.newsapp.com.example.newsapp.view_models.Result
import com.example.newsapp.modules.NewsResponse
import io.mockk.coEvery
import org.junit.Assert.*
import org.junit.Test
import retrofit2.Response

class DataLoaderViewModelTest{

    @Test
    fun `test getResponse function with success`() {
        val dataLoaderViewModel = viewModel<DataLoaderViewModel>()
        dataLoaderViewModel.getArticles(dataLoaderViewModel.searchCategory.value)
        val response = dataLoaderViewModel.newsResponse.observeAsState(Result.loading()).value

        if(response is Result.Success) {
            assertTrue(response.data is NewsResponse)
        }

    }

    @Test
    fun `test getResponse function with error`() {
        val dataLoaderViewModel = viewModel<DataLoaderViewModel>()
        dataLoaderViewModel.getArticles(dataLoaderViewModel.searchCategory.value)
        val response = dataLoaderViewModel.newsResponse.observeAsState(Result.loading()).value

        if(response is Result.Error) {
            assertTrue(response.exception is Exception)
        }

    }

    @Test
    fun `test getResponse function with loading`() {
        val dataLoaderViewModel = viewModel<DataLoaderViewModel>()
        dataLoaderViewModel.getArticles(dataLoaderViewModel.searchCategory.value)
        val response = dataLoaderViewModel.newsResponse.observeAsState(Result.loading()).value

        if(response is Result.Loading) {
            assertTrue(response.message is String)
        }

    }
}