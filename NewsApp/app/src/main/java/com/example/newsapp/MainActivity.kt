package com.example.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.newsapp.modules.NewsResponse
import com.example.newsapp.ui.theme.NewsAppTheme

class MainActivity : ComponentActivity() {

    val dataLoaderViewModel by viewModels<DataLoaderViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataLoaderViewModel.getArticles()

        setContent {
            NewsAppTheme {
                val articlesResult = dataLoaderViewModel.newsResponse.observeAsState(Result.loading()).value

                Column {
                    /*Button(onClick = { dataLoaderViewModel.getArticles() }) {
                        Text(text = "Get Posts")
                    }*/

                    ArticleList(articlesResult)
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
                            article.imageUrl?.let { url ->
                                val image = loadPicture(url, DEFAULT_IMAGE ).value
                                image?.let { img ->
                                    Image(bitmap = img.asImageBitmap(), "articleImage", modifier = Modifier.fillMaxWidth(), contentScale = ContentScale.Crop);
                                }

                            }
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
        is Result.Loading -> {
            Image(painterResource(id = R.drawable.loading), "articleImage", modifier = Modifier.fillMaxWidth().fillMaxHeight());
        }
    }
}