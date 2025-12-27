package com.rahulpatel.newsapp.repository

import app.cash.turbine.test
import com.rahulpatel.newsapp.data.api.NetworkService
import com.rahulpatel.newsapp.data.model.topheadlines.ApiArticle
import com.rahulpatel.newsapp.data.model.topheadlines.ApiSource
import com.rahulpatel.newsapp.data.model.topheadlines.TopHeadlinesResponse
import com.rahulpatel.newsapp.data.repository.TopHeadlineRepository
import com.rahulpatel.newsapp.utils.AppConstant
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.doThrow
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TopHeadlineRepositoryTest {

    @Mock
    private lateinit var networkService: NetworkService

    private lateinit var topHeadlineRepository: TopHeadlineRepository

    @Before
    fun setUp() {
        topHeadlineRepository = TopHeadlineRepository(networkService)
    }
    //If API succeeds → repository emits articles
    @Test
    fun getTopHeadlines_whenNetworkServiceResponseSuccess_shouldReturnSuccess() {
        runTest {
            val country = AppConstant.COUNTRY
            val apiSource = ApiSource(id = "SourceId", name = "sourceName")
            val apiArticle = ApiArticle(
                title = "title",
                description = "description",
                url = "url",
                imageUrl = "urlToImage",
                apiSource = apiSource
            )

            val listOfArticleAPI = mutableListOf<ApiArticle>()
            listOfArticleAPI.add(apiArticle)

            val topHeadlinesResponse = TopHeadlinesResponse(
                status = "ok", totalResults = 1, apiArticles = listOfArticleAPI
            )

            doReturn(topHeadlinesResponse)
                .`when`(networkService).getTopHeadlines(country)

            topHeadlineRepository.getTopHeadlinesArticles(country).test {
                assertEquals(topHeadlinesResponse.apiArticles, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

            verify(networkService, times(1)).getTopHeadlines(country)
        }
    }

    //If API fails → repository emits error
    @Test
    fun getTopHeadlines_whenNetworkServiceResponseError_shouldReturnError() {
        runTest {
            val country = AppConstant.COUNTRY
            val errorMessage = "Enter Message For You"

            doThrow(RuntimeException(errorMessage)).`when`(networkService)
                .getTopHeadlines(country)

            topHeadlineRepository.getTopHeadlinesArticles(country).test {
                assertEquals(errorMessage, awaitError().message)
                cancelAndIgnoreRemainingEvents()
            }
            verify(networkService, times(1)).getTopHeadlines(country)
        }
    }
}