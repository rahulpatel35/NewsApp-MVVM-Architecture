package com.rahulpatel.newsapp.data.repository

import com.rahulpatel.newsapp.data.api.NetworkService
import com.rahulpatel.newsapp.data.local.DatabaseService
import com.rahulpatel.newsapp.data.local.entity.Article
import com.rahulpatel.newsapp.data.model.topheadlines.toArticleEntity
import com.rahulpatel.newsapp.di.ActivityScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ActivityScope
@OptIn(ExperimentalCoroutinesApi::class)
class NewsRepository @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService
) {

    fun getNewsBySources(sourceId: String): Flow<List<Article>> {
        return flow { emit(networkService.getNewsBySources(sourceId)) }
            .map {
                it.apiArticles.map { apiArticle -> apiArticle.toArticleEntity(sourceId) }
            }.flatMapConcat { articles ->
                flow { emit(databaseService.deleteAllAndInsertAllSourceNews(articles, sourceId)) }
            }.flatMapConcat {
                databaseService.getSourceNewsByDB(sourceId)
            }
    }

    fun getNewsBySourceByDB(sourceId: String): Flow<List<Article>> {
        return databaseService.getSourceNewsByDB(sourceId)
    }


    fun getNewsByCountry(countryId: String): Flow<List<Article>> {
        return flow { emit(networkService.getNewsByCountry(countryId)) }
            .map {
                it.apiArticles.map { apiArticle -> apiArticle.toArticleEntity(countryId) }
            }.flatMapConcat { articles ->
                flow {
                    emit(
                        databaseService.deleteAndInsertAllTopHeadlinesArticles(articles, countryId)
                    )
                }
            }.flatMapConcat {
                databaseService.getAllTopHeadlinesArticles(countryId)
            }
    }

    fun getNewsByCountryByDB(countryId: String): Flow<List<Article>> {
        return databaseService.getAllTopHeadlinesArticles(countryId)
    }


}