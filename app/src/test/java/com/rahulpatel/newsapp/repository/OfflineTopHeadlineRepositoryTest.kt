package com.rahulpatel.newsapp.repository

import app.cash.turbine.test
import com.rahulpatel.newsapp.data.local.DatabaseService
import com.rahulpatel.newsapp.data.api.NetworkService
import com.rahulpatel.newsapp.data.local.entity.Article
import com.rahulpatel.newsapp.data.local.entity.Source
import com.rahulpatel.newsapp.data.model.topheadlines.ApiArticle
import com.rahulpatel.newsapp.data.model.topheadlines.ApiSource
import com.rahulpatel.newsapp.data.model.topheadlines.TopHeadlinesResponse
import com.rahulpatel.newsapp.data.repository.OfflineTopHeadlineRepository
import com.rahulpatel.newsapp.utils.AppConstant
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.doThrow
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import kotlin.concurrent.timer
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class OfflineTopHeadlineRepositoryTest {
    @Mock
    private lateinit var networkService: NetworkService

    @Mock
    private lateinit var databaseService: DatabaseService

    private lateinit var offlineTopHeadlineRepository: OfflineTopHeadlineRepository

    @Before
    fun setup() {
        offlineTopHeadlineRepository =
            OfflineTopHeadlineRepository(networkService, databaseService)
    }

    //When the network API returns data successfully, the repository should emit the same data.
    //This test ensures that when the API succeeds, the repository emits the same articles
    // and calls the network exactly once.
    @Test
    fun getTopHeadline_whenNetworkServiceResponseSuccess_shouldReturnSuccess() {
        runTest {
            val country = AppConstant.COUNTRY
            val apiSource = ApiSource(id = "sourceId", name = "sourceName")
            val apiArticle = ApiArticle(
                title = "title",
                description = "description",
                url = "url",
                imageUrl = "urlToImage",
                apiSource = apiSource
            )

            val listOfArticleApi = mutableListOf<ApiArticle>()
            listOfArticleApi.add(apiArticle)

            val topHeadlinesResponse = TopHeadlinesResponse(
                status = "ok",
                totalResults = 1,
                apiArticles = listOfArticleApi
            )

            doReturn(topHeadlinesResponse).`when`(networkService).getTopHeadlines(country)

            offlineTopHeadlineRepository.getTopHeadlinesArticles(AppConstant.COUNTRY).test {
                assertEquals(topHeadlinesResponse.apiArticles, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

            verify(networkService, times(1)).getTopHeadlines(country)

        }
    }

    // Network error handling
    //If the API throws an error, the repository should emit the same error.
    @Test
    fun getTopHeadlines_whenNetworkServiceResponseError_shouldReturnError() {
        runTest {
            val country = AppConstant.COUNTRY
            val errorMessage = "Enter Message For You"

            doThrow(RuntimeException(errorMessage)).`when`(networkService)
                .getTopHeadlines(country)

            offlineTopHeadlineRepository.getTopHeadlinesArticles(country).test {
                assertEquals(errorMessage, awaitError().message)
                cancelAndIgnoreRemainingEvents()
            }
            verify(networkService, times(1)).getTopHeadlines(country)
        }
    }

    //Database success handling
    // If database returns cached articles, the repository should emit them.
    @Test
    fun getTopHeadlinesFromDB_whenDatabaseServiceResponseSuccess_shouldReturnSuccess() {
        runTest {
            val country = AppConstant.COUNTRY
            val source = Source(
                id = "SourceId", name = "sourceName"
            )
            val article = Article(
                title = "title",
                description = "description",
                url = "url",
                imageUrl = "urlToImage",
                source = source
            )
            val listOfArticle = mutableListOf<Article>()
            listOfArticle.add(article)

            doReturn(flowOf(listOfArticle)).`when`(databaseService)
                .getAllTopHeadlinesArticles(country)

            offlineTopHeadlineRepository.getTopHeadlinesArticlesFromDB(country).test {
                assertEquals(listOfArticle, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

            verify(databaseService, times(1)).getAllTopHeadlinesArticles(country)
        }
    }
}



