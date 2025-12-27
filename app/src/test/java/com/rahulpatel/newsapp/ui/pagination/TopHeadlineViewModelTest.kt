package com.rahulpatel.newsapp.ui.pagination

import app.cash.turbine.test
import com.rahulpatel.newsapp.data.local.entity.Article
import com.rahulpatel.newsapp.data.repository.TopHeadlineRepository
import com.rahulpatel.newsapp.ui.base.UiState
import com.rahulpatel.newsapp.ui.topheadline.TopHeadlineViewModel
import com.rahulpatel.newsapp.utils.AppConstant
import com.rahulpatel.newsapp.utils.DispatcherProvider
import com.rahulpatel.newsapp.utils.NetworkHelper
import com.rahulpatel.newsapp.utils.TestDispatcherProvider
import com.rahulpatel.newsapp.utils.TestLogger
import com.rahulpatel.newsapp.utils.TestNetworkHelper
import com.rahulpatel.newsapp.utils.logger.Logger
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

//This test ensures TopHeadlineViewModel emits correct UI states for success and error scenarios.
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TopHeadlineViewModelTest {

    @Mock
    private lateinit var topHeadlinesRepository: TopHeadlineRepository

    private lateinit var dispatcherProvider: DispatcherProvider

    private lateinit var networkHelper: NetworkHelper

    private lateinit var logger: Logger

    @Before
    fun setUp() {
        dispatcherProvider = TestDispatcherProvider()
        networkHelper = TestNetworkHelper()
        logger = TestLogger()
    }

    @Test
    fun fetchNews_whenRepositoryResponseSuccess_shouldSetSuccessUiState() {
        runTest {
            val country = AppConstant.COUNTRY
            doReturn(flowOf(emptyList<Article>()))
                .`when`(topHeadlinesRepository)
                .getTopHeadlinesArticles(country)
            val viewModel = TopHeadlineViewModel(
                topHeadlinesRepository,
                dispatcherProvider,
                networkHelper,
                logger
            )
            viewModel.topHeadLineUiState.test {
                assertEquals(UiState.Success(emptyList<List<Article>>()), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            verify(topHeadlinesRepository, times(1)).getTopHeadlinesArticles(country)
        }
    }

    @Test
    fun fetchNews_whenRepositoryResponseError_shouldSetErrorUiState() {
        runTest {
            val country = AppConstant.COUNTRY
            val errorMessage = "Error Message For You"
            doReturn(flow<List<Article>> {
                throw IllegalStateException(errorMessage)
            })
                .`when`(topHeadlinesRepository)
                .getTopHeadlinesArticles(country)

            val viewModel = TopHeadlineViewModel(
                topHeadlinesRepository,
                dispatcherProvider,
                networkHelper,
                logger
            )
            viewModel.topHeadLineUiState.test {
                assertEquals(
                    UiState.Error(IllegalStateException(errorMessage).toString()),
                    awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
            verify(topHeadlinesRepository, times(1)).getTopHeadlinesArticles(country)
        }
    }

}