package com.rahulpatel.newsapp.ui.topheadline

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rahulpatel.newsapp.data.local.entity.Article
import com.rahulpatel.newsapp.ui.base.ArticleList
import com.rahulpatel.newsapp.ui.base.ShowError
import com.rahulpatel.newsapp.ui.base.ShowLoading
import com.rahulpatel.newsapp.ui.base.UiState

@Composable
fun TopHeadLineRoute(
    onNewsClick: (url: String) -> Unit, topHeadLineViewModel: TopHeadlineViewModel = hiltViewModel()
) {
    val topHeadLineUiState: UiState<List<Article>> by topHeadLineViewModel.topHeadLineUiState.collectAsStateWithLifecycle()
    Column(modifier = Modifier.padding(4.dp)) {
        TopHeadlineScreen(topHeadLineUiState, onNewsClick, onRetryClick = {
            topHeadLineViewModel.startFetchingArticle()
        })
    }
}

@Composable
fun TopHeadlineScreen(
    uiState: UiState<List<Article>>, onNewsClick: (String) -> Unit, onRetryClick: () -> Unit
) {
    when (uiState) {
        is UiState.Success -> {
            ArticleList(uiState.data, onNewsClick)
        }

        is UiState.Loading -> {
            ShowLoading()
        }

        is UiState.Error -> {
            ShowError(text = uiState.message) {
                onRetryClick()
            }
        }
    }
}