package com.rahulpatel.newsapp.ui.offline

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
fun OfflineTopHeadlineRoute(
    onNewsClick: (uri: String) -> Unit,
    offlineTopHeadlineViewModel: OfflineTopHeadlineViewModel = hiltViewModel()
) {
    val offlineTopHeadlineUiState: UiState<List<Article>> by offlineTopHeadlineViewModel.topHeadlineUiState.collectAsStateWithLifecycle()
    Column(modifier = Modifier.padding(4.dp)) {
        OfflineTopHeadlineScreen(offlineTopHeadlineUiState, onNewsClick, onRetryClick = {
            offlineTopHeadlineViewModel.startFetchingArticles()
        })
    }
}

@Composable
fun OfflineTopHeadlineScreen(
    uiState: UiState<List<Article>>,
    onNewsClick: (String) -> Unit,
    onRetryClick: () -> Unit
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