package com.rahulpatel.newsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rahulpatel.newsapp.data.api.NetworkService
import com.rahulpatel.newsapp.data.model.topheadlines.ApiArticle
import com.rahulpatel.newsapp.di.ActivityScope
import com.rahulpatel.newsapp.utils.AppConstant.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ActivityScope
class PaginationTopHeadlineRepository @Inject constructor(private val networkService: NetworkService) {
    fun getTopHeadlinesArticles(): Flow<PagingData<ApiArticle>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                TopHeadlinePagingSource(networkService)
            }).flow
    }
}