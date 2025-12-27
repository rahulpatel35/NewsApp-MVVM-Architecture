package com.rahulpatel.newsapp.ui.pagination

import androidx.paging.PagingSource
import com.rahulpatel.newsapp.data.api.NetworkService
import com.rahulpatel.newsapp.data.model.topheadlines.ApiArticle
import com.rahulpatel.newsapp.data.model.topheadlines.TopHeadlinesResponse
import com.rahulpatel.newsapp.data.repository.TopHeadlinePagingSource
import com.rahulpatel.newsapp.utils.AppConstant
import com.rahulpatel.newsapp.utils.DispatcherProvider
import com.rahulpatel.newsapp.utils.NetworkHelper
import com.rahulpatel.newsapp.utils.TestDispatcherProvider
import com.rahulpatel.newsapp.utils.TestLogger
import com.rahulpatel.newsapp.utils.TestNetworkHelper
import com.rahulpatel.newsapp.utils.logger.Logger
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

//This test ensures PagingSource correctly returns Page on success and Error on failure.
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class NewsPagingSourceTest {

    @Mock
    private lateinit var networkService: NetworkService

    private lateinit var pagingSource: TopHeadlinePagingSource

    private lateinit var dispatcherProvider: DispatcherProvider

    private lateinit var networkHelper: NetworkHelper

    private lateinit var logger: Logger

    @Before
    fun setUp() {
        dispatcherProvider = TestDispatcherProvider()
        networkHelper = TestNetworkHelper()
        logger = TestLogger()
        pagingSource = TopHeadlinePagingSource(networkService)
    }

    @Test
    fun fetchNews_whenRepositoryResponseSuccess_shouldSetSuccessUiState() {
        runTest {
            // Given
            val page = 1
            val articles = emptyList<ApiArticle>()

            val topHeadlinesResponse =
                TopHeadlinesResponse(status = "ok", totalResults = 0, apiArticles = articles)

            Mockito.doReturn(topHeadlinesResponse)
                .`when`(networkService).getTopHeadlines(
                    country = AppConstant.COUNTRY,
                    page = page,
                    pageSize = AppConstant.PAGE_SIZE
                )

            // When
            val result = pagingSource.load(PagingSource.LoadParams.Refresh(page, 1, true))

            // Then
            val expected = PagingSource.LoadResult.Page(
                data = articles,
                prevKey = null,
                nextKey = null
            )

            Assert.assertEquals(expected, result)

            Mockito.verify(networkService, Mockito.times(1)).getTopHeadlines(
                country = AppConstant.COUNTRY,
                page = page,
                pageSize = AppConstant.PAGE_SIZE
            )
        }
    }

    @Test
    fun fetchNews_whenRepositoryResponseError_shouldSetErrorUiState() {
        runTest {
            // Given
            val page = 1
            val error = RuntimeException("Error Message For You")
            Mockito.doThrow(error)
                .`when`(networkService).getTopHeadlines(
                    country = AppConstant.COUNTRY,
                    page = page,
                    pageSize = AppConstant.PAGE_SIZE
                )

            // When
            val result = pagingSource.load(PagingSource.LoadParams.Refresh(page, 1, false))

            // Then
            val expected = PagingSource.LoadResult.Error<Int, ApiArticle>(error)
            Assert.assertEquals(expected.toString(), result.toString())

            Mockito.verify(networkService, Mockito.times(1)).getTopHeadlines(
                country = AppConstant.COUNTRY,
                page = page,
                pageSize = AppConstant.PAGE_SIZE
            )
        }
    }
}