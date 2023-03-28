package com.example.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.newsapp.modules.NewsResponse
import com.example.newsapp.ui.theme.NewsAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataLoaderViewModel by viewModels<DataLoaderViewModel>()
        setContent {
            NewsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val articlesResult = dataLoaderViewModel.newsResponse.observeAsState(Result.loading()).value
                    Column {
                        /*Button(onClick = { dataLoaderViewModel.getArticles() }) {
                            Text(text = "Get Posts")
                        }*/
                        dataLoaderViewModel.getArticles()
                        ArticleList(articlesResult)
                    }
                }
            }
        }


    }
}

@Composable
fun ArticleList(newsResult: Result<NewsResponse>) {


    when (newsResult) {
        is Result.Success -> {
            val newsResponse = newsResult.data
            println(newsResponse)
            LazyColumn {
                items(newsResponse.articles) { article ->
                    Card(
                        Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    ) {
                        Column(
                            Modifier.padding(8.dp)
                        ) {
                            article.title?.let { Text(text = it, style = MaterialTheme.typography.h5) }
                            article.description?.let { Text(text = it, style = MaterialTheme.typography.body1) }
                        }
                    }
                }
            }
        }
        is Result.Error -> {
            Text(text = "Error: ${newsResult.exception.message}")
        }
        else -> {

        }
    }
}