package com.rahulpatel.newsapp.data.repository

import com.rahulpatel.newsapp.data.api.NetworkService
import com.rahulpatel.newsapp.data.local.entity.Article
import com.rahulpatel.newsapp.data.model.topheadlines.toArticleEntity
import com.rahulpatel.newsapp.di.ActivityScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ActivityScope
class SearchRepository @Inject constructor(private val networkService: NetworkService) {

    fun getNewsByQueries(queries: String): Flow<List<Article>> {
        return flow {
            emit(networkService.getNewsByQueries(queries))
        }.map {
            it.apiArticles.map { apiArticle -> apiArticle.toArticleEntity(queries) }
        }
    }
}