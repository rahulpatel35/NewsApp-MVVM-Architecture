package com.rahulpatel.newsapp.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rahulpatel.newsapp.data.api.NetworkService
import com.rahulpatel.newsapp.data.model.topheadlines.ApiArticle
import com.rahulpatel.newsapp.utils.AppConstant.INITIAL_PAGE
import com.rahulpatel.newsapp.utils.AppConstant.COUNTRY
import com.rahulpatel.newsapp.utils.AppConstant.PAGE_SIZE

class TopHeadlinePagingSource constructor(private val networkService: NetworkService) :
    PagingSource<Int, ApiArticle>() {
    override fun getRefreshKey(state: PagingState<Int, ApiArticle>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ApiArticle> {
        return try {
            val page = params.key ?: INITIAL_PAGE
            val response = networkService.getTopHeadlines(
                country = COUNTRY, page = page, pageSize = PAGE_SIZE
            )

            LoadResult.Page(
                data = response.apiArticles,
                prevKey = if (page == INITIAL_PAGE) null else page.minus(1),
                nextKey = if (response.apiArticles.isEmpty()) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}