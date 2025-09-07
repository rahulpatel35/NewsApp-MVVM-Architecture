package com.rahulpatel.newsapp.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.rahulpatel.newsapp.data.model.repository.OfflineTopHeadlineRepository
import com.rahulpatel.newsapp.data.model.repository.PaginationTopHeadlineRepository
import com.rahulpatel.newsapp.data.model.repository.TopHeadlineRepository
import com.rahulpatel.newsapp.di.ActivityContext
import com.rahulpatel.newsapp.ui.base.ViewModelProviderFactory
import com.rahulpatel.newsapp.ui.offline.OfflineTopHeadlineViewModel
import com.rahulpatel.newsapp.ui.pagination.PaginationTopHeadlineAdapter
import com.rahulpatel.newsapp.ui.pagination.PaginationTopHeadlineViewModel
import com.rahulpatel.newsapp.ui.topheadline.TopHeadlineAdapter
import com.rahulpatel.newsapp.ui.topheadline.TopHeadlineViewModel
import com.rahulpatel.newsapp.utils.DispatcherProvider
import com.rahulpatel.newsapp.utils.NetworkHelper
import com.rahulpatel.newsapp.utils.logger.Logger
import dagger.Module
import dagger.Provides


@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @ActivityContext
    @Provides
    fun provideContext(): Context {
        return activity
    }

    @Provides
    fun provideTopHeadLinesViewModel(
        topHeadlineRepository: TopHeadlineRepository,
        networkHelper: NetworkHelper,
        dispatcherProvider: DispatcherProvider,
        logger: Logger
    ): TopHeadlineViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(TopHeadlineViewModel::class) {
            TopHeadlineViewModel(
                topHeadlineRepository, dispatcherProvider, networkHelper, logger
            )
        })[TopHeadlineViewModel::class.java]
    }

    @Provides
    fun provideTopHeadlineAdapter() = TopHeadlineAdapter(ArrayList())

    @Provides
    fun providePaginationTopHeadlineAdapter() = PaginationTopHeadlineAdapter()

    @Provides
    fun provideOfflineTopHeadlineViewModel(
        offlineTopHeadlineRepository: OfflineTopHeadlineRepository,
        networkHelper: NetworkHelper,
        dispatcherProvider: DispatcherProvider,
        logger: Logger
    ): OfflineTopHeadlineViewModel {
        return ViewModelProvider(
            activity,
            ViewModelProviderFactory(OfflineTopHeadlineViewModel::class) {
                OfflineTopHeadlineViewModel(
                    offlineTopHeadlineRepository, dispatcherProvider, networkHelper, logger
                )
            })[OfflineTopHeadlineViewModel::class.java]
    }

    @Provides
    fun providePaginationTopHeadLinesViewModel(
        paginationTopHeadlineRepository: PaginationTopHeadlineRepository,
        dispatcherProvider: DispatcherProvider
    ): PaginationTopHeadlineViewModel {
        return ViewModelProvider(
            activity,
            ViewModelProviderFactory(PaginationTopHeadlineViewModel::class) {
                PaginationTopHeadlineViewModel(
                    paginationTopHeadlineRepository, dispatcherProvider
                )
            })[PaginationTopHeadlineViewModel::class.java]
    }
}