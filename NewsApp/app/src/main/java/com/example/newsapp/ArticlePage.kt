package com.example.newsapp.com.example.newsapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.newsapp.DEFAULT_IMAGE
import com.example.newsapp.loadPicture
import com.example.newsapp.modules.Article


@Composable
fun ArticlePage(article : Article) {



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