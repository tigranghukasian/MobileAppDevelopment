package com.example.newsapp.com.example.newsapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.newsapp.*
import com.example.newsapp.R
import com.example.newsapp.modules.Article

@Composable
fun MainPage(navController: NavHostController, articles: List<Article>) {
    Column {
        Box(Modifier.fillMaxWidth().height(130.dp).background(MaterialTheme.colors.primaryVariant).padding(10.dp)) {
            Column {
                Text("News", fontSize = 40.sp, color = Color.White, modifier = Modifier.padding(bottom = 10.dp))
                Row {
                    SearchField()
                    Button(onClick = {
                    },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White
                        )
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.filter),
                            contentDescription = "Filter",
                            colorFilter  = ColorFilter.tint(MaterialTheme.colors.primaryVariant)
                        )

                    }

                }

            }

        }
        ArticleList(articles) {
            println("CLICKED ON ARTICLE " + it)
            navController.navigate(Destination.ArticlePage.createRoute(it))
        }
    }

}
@Composable
fun SearchField() {
    var text: String by remember { mutableStateOf("") }


    TextField(value = text, onValueChange = {
        text = it

    }, modifier = Modifier.padding(end = 20.dp), colors = TextFieldDefaults.textFieldColors(
        textColor = Color.DarkGray,
        backgroundColor = Color.White
    )
    )

}


@Composable
fun ArticleList(articleList : List<Article>, onArticleClick : (Int) -> Unit) {


    LazyColumn {
        itemsIndexed(articleList) {index, article ->
            Card(
                Modifier
                    .padding(8.dp)
                    .fillMaxWidth().clickable { onArticleClick(index)
                    }
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


fun OnSearchFieldValueChanged (value: String) {

}
