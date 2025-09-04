package com.rahulpatel.newsapp.ui.topheadline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahulpatel.newsapp.data.model.repository.TopHeadlineRepository
import com.rahulpatel.newsapp.data.model.topheadlines.Article
import com.rahulpatel.newsapp.ui.base.UiState
import com.rahulpatel.newsapp.utils.AppConstant
import com.rahulpatel.newsapp.utils.DispatcherProvider
import com.rahulpatel.newsapp.utils.NetworkHelper
import com.rahulpatel.newsapp.utils.logger.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

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
            fetchingArticle()
        } else {
            _topHeadlineUiState.value = UiState.Error("Data not found")
        }
    }

    private fun fetchingArticle() {
        viewModelScope.launch(dispatcherProvider.main) {
            topHeadlineRepository.getTopHeadlinesArticles(AppConstant.COUNTRY)
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    _topHeadlineUiState.value = UiState.Error(e.toString())
                    logger.d(TAG, "Success")
                }
                .collect {

                }
        }
    }


}