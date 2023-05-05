package com.example.newsapp.com.example.newsapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.*
import com.example.newsapp.R
import com.example.newsapp.com.example.newsapp.view_models.DataLoaderViewModel
import com.example.newsapp.modules.Article
import com.example.newsapp.com.example.newsapp.view_models.Result

import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainPage(navController: NavHostController) {
    val dataLoaderViewModel = viewModel<DataLoaderViewModel>()

    val articlesResult = dataLoaderViewModel.newsResponse.observeAsState(Result.loading()).value

    var menuVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        dataLoaderViewModel.getArticles("")
    }
    when (articlesResult) {
        is Result.Success -> {
            Scaffold(bottomBar = {

                if(menuVisible) {
                    BottomFilterOptions()
                }})
            {
                Column {
                    Box(
                        Modifier.fillMaxWidth().height(130.dp).background(MaterialTheme.colors.primaryVariant)
                            .padding(10.dp)
                    ) {
                        Column {
                            Text("News", fontSize = 40.sp, color = Color.White, modifier = Modifier.padding(bottom = 10.dp))
                            Row {
                                SearchField()
                                Button(
                                    onClick = {
                                              menuVisible = !menuVisible
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color.White
                                    )
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.filter),
                                        contentDescription = "Filter",
                                        colorFilter = ColorFilter.tint(MaterialTheme.colors.primaryVariant)
                                    )

                                }

                            }

                        }

                    }
                    SwipeRefreshCompose(navController, dataLoaderViewModel, articlesResult.data.articles)
                    ////

                }
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
@Composable
fun BottomFilterOptions() {
    val dataLoaderViewModel = viewModel<DataLoaderViewModel>()
    val buttonLabels = listOf("Business", "Entertainment", "General", "Health")
    var selectedButtonIndex by remember { mutableStateOf(0) }

    Card(Modifier.fillMaxWidth().height(300.dp).background(Color.White)) {
        MultipleButtonsWithOneSelected(selectedButtonIndex, buttonLabels, onButtonClicked = {
                index -> selectedButtonIndex = index
        }, onApplyClicked = {
            dataLoaderViewModel.getArticles(it.lowercase())
            //println("get articles with new category" + it.lowercase())
        })
    }


}

@Composable
fun MultipleButtonsWithOneSelected(selectedButtonIndex: Int, buttonLabels: List<String>, onButtonClicked: (Int) -> Unit, onApplyClicked : (String) -> Unit) {
    Column( Modifier.fillMaxWidth().height(300.dp).background(Color.White), horizontalAlignment = Alignment.CenterHorizontally) {
            buttonLabels.forEachIndexed { index, label ->
                val isSelected = index == selectedButtonIndex
                Button(
                    onClick = { onButtonClicked(index) },
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                        .fillMaxWidth(),
                    colors = if (isSelected) ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant) else ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    content = {
                        Text(text = label, color = if (isSelected) Color.White else Color.Black)
                    }
                )
            }
            Button(
                onClick = {onApplyClicked(buttonLabels[selectedButtonIndex]) },
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
                    .fillMaxWidth(),
                content = {
                    Text(text = "Apply")
                }
            )
    }
}

@Composable
fun SwipeRefreshCompose(navController: NavController, dataLoaderViewModel: DataLoaderViewModel, articles : List<Article>) {

    var refreshing by remember { mutableStateOf(false) }
    LaunchedEffect(refreshing) {
        if (refreshing) {
            delay(3000)
            refreshing = false
        }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = refreshing),
        onRefresh = {
            refreshing = true
            //dataLoaderViewModel.getArticles(dataLoaderViewModel.searchCategory.value)
                    },
    ) {

        ArticleList(articles) {
            navController.navigate(Destination.ArticlePage.createRoute(it))
        }

    }

}

@Composable
fun SearchField() {
    var text: String by remember { mutableStateOf("") }

    val dataLoaderViewModel = viewModel<DataLoaderViewModel>()

    TextField(value = text, onValueChange = {
        text = it
        OnSearchFieldValueChanged(text)
    }, modifier = Modifier.padding(end = 20.dp),
        leadingIcon = {
            IconButton(onClick = {
                dataLoaderViewModel.getArticles("", text)
            })
            {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "search Icon"
                )
            }

        },
        trailingIcon = {
            IconButton(onClick = {
                text = ""
            })
            {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "clear Icon"
                )
            }

        },
        colors = TextFieldDefaults.textFieldColors(
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
