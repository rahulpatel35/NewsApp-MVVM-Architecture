package com.rahulpatel.newsapp.ui.pagination

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.rahulpatel.newsapp.data.model.topheadlines.ApiArticle
import com.rahulpatel.newsapp.data.model.topheadlines.ApiSource
import com.rahulpatel.newsapp.ui.base.ShowError
import com.rahulpatel.newsapp.ui.base.ShowLoading


@Composable
fun PaginationTopHeadlineRoute(
    onNewsClick: (uri: String) -> Unit,
    paginationTopHeadlineViewModel: PaginationTopHeadlineViewModel = hiltViewModel()
) {
    val paginationTopHeadlineUiState =
        paginationTopHeadlineViewModel.topHeadlineUiState.collectAsLazyPagingItems()
    Column(modifier = Modifier.padding(4.dp)) {
        TopHeadlineScreen(paginationTopHeadlineUiState, onNewsClick)
    }
}

@Composable
fun TopHeadlineScreen(articles: LazyPagingItems<ApiArticle>, onNewClick: (url: String) -> Unit) {

    ArticleList(articles, onNewClick)

    articles.apply {
        when {
            loadState.refresh is LoadState.Loading -> {
                ShowLoading()
            }

            loadState.refresh is LoadState.Error -> {
                val error = articles.loadState.refresh as LoadState.Error
                ShowError(error.error.localizedMessage!!)
            }

            loadState.append is LoadState.Loading -> {
                ShowLoading()
            }
        }
    }
}

@Composable
fun ArticleList(
    articles: LazyPagingItems<ApiArticle>,
    onNewsClick: (String) -> Unit
) {
    LazyColumn {
        items(articles.itemCount, key = { index -> articles[index]!!.url }) { index ->
            ApiArticle(articles[index]!!, onNewsClick)
        }
    }
}


@Composable
fun ApiArticle(article: ApiArticle, onNewClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (article.url.isNotEmpty()) {
                    onNewClick(article.url)
                }
            }
    ) {
        BannerImage(article)
        TitleText(article.title)
        DescriptionText(article.description)
        SourceText(article.apiSource)
    }
}

@Composable
fun BannerImage(article: ApiArticle) {
    AsyncImage(
        model = article.imageUrl,
        contentDescription = article.title,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
    )
}

@Composable
fun TitleText(title: String) {
    if (title.isNotEmpty()) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black,
            maxLines = 2,
            modifier = Modifier.padding(4.dp)
        )
    }
}

@Composable
fun DescriptionText(description: String?) {
    if (!description.isNullOrEmpty()) {
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            maxLines = 2,
            modifier = Modifier.padding(4.dp)
        )
    }
}

@Composable
fun SourceText(source: ApiSource) {
    Text(
        text = source.name,
        style = MaterialTheme.typography.titleSmall,
        color = Color.Gray,
        maxLines = 1,
        modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 8.dp)
    )
}
