package com.rahulpatel.newsapp.ui.topheadline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahulpatel.newsapp.data.local.entity.Article
import com.rahulpatel.newsapp.data.repository.TopHeadlineRepository
import com.rahulpatel.newsapp.data.model.topheadlines.toArticleEntity
import com.rahulpatel.newsapp.ui.base.UiState
import com.rahulpatel.newsapp.utils.AppConstant
import com.rahulpatel.newsapp.utils.DispatcherProvider
import com.rahulpatel.newsapp.utils.NetworkHelper
import com.rahulpatel.newsapp.utils.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopHeadlineViewModel @Inject constructor(
    private val topHeadlineRepository: TopHeadlineRepository,
    private val dispatcherProvider: DispatcherProvider,
    private val networkHelper: NetworkHelper,
    private val logger: Logger
) : ViewModel() {
    private val TAG: String = "TopHeadLineViewModel"
    private val _topHeadlineUiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)

    val topHeadLineUiState: StateFlow<UiState<List<Article>>> = _topHeadlineUiState

    private fun checkInternetConnection(): Boolean = networkHelper.isNetworkConnected()

    init {
        startFetchingArticle()
    }

    fun startFetchingArticle() {
        if (checkInternetConnection()) {
            fetchArticles()
        } else {
            _topHeadlineUiState.value = UiState.Error("Data not found")
        }
    }

    private fun fetchArticles() {
        viewModelScope.launch(dispatcherProvider.main) {
            topHeadlineRepository.getTopHeadlinesArticles(AppConstant.COUNTRY)
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    _topHeadlineUiState.value = UiState.Error(e.toString())
                }.map {
                    it.map { apiArticle -> apiArticle.toArticleEntity(AppConstant.COUNTRY) }
                }.collect {
                    _topHeadlineUiState.value = UiState.Success(it)
                    logger.d("TopHeadlineViewModel", "Success")
                }
        }
    }
}