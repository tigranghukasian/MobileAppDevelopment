package com.example.newsapp.com.example.newsapp

import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.DEFAULT_IMAGE
import com.example.newsapp.com.example.newsapp.view_models.DataLoaderViewModel
import com.example.newsapp.com.example.newsapp.view_models.Result
import com.example.newsapp.loadPicture
import com.example.newsapp.modules.Article
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsapp.Destination
import com.example.newsapp.R


@Composable
fun ArticlePage(articleId : Int ) {


    val dataLoaderViewModel = viewModel<DataLoaderViewModel>()
    //dataLoaderViewModel.getArticles(dataLoaderViewModel.searchCategory.value)
    val articlesResult = dataLoaderViewModel.newsResponse.observeAsState(Result.loading()).value

    when (articlesResult) {
        is Result.Success -> {
            val article = articlesResult.data.articles[articleId]
            Column(
                Modifier.padding(8.dp)
            ) {
                article.imageUrl?.let { url ->
                    val image = loadPicture(url, DEFAULT_IMAGE).value
                    image?.let { img ->
                        Image(
                            bitmap = img.asImageBitmap(),
                            "articleImage",
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop
                        );
                    }

                }
                article.title?.let { Text(text = it, style = MaterialTheme.typography.h5) }
                article.description?.let { Text(text = it, style = MaterialTheme.typography.body1) }
            }
        }

        is Result.Error -> {
            Text(text = "Error: ${articlesResult.exception.message}")
        }

        is Result.Loading -> {
            Image(
                painterResource(id = R.drawable.loading),
                "articleImage",
                modifier = Modifier.fillMaxWidth().fillMaxHeight()
            );
        }
    }
}