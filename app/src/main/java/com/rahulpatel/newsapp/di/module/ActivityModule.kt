package com.rahulpatel.newsapp.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.rahulpatel.newsapp.data.repository.CountryListRepository
import com.rahulpatel.newsapp.data.repository.LanguageListRepository
import com.rahulpatel.newsapp.data.repository.NewsRepository
import com.rahulpatel.newsapp.data.repository.OfflineTopHeadlineRepository
import com.rahulpatel.newsapp.data.repository.PaginationTopHeadlineRepository
import com.rahulpatel.newsapp.data.repository.TopHeadlineRepository
import com.rahulpatel.newsapp.di.ActivityContext
import com.rahulpatel.newsapp.ui.base.ViewModelProviderFactory
import com.rahulpatel.newsapp.ui.country.CountryListAdapter
import com.rahulpatel.newsapp.ui.country.CountryListViewModel
import com.rahulpatel.newsapp.ui.language.LanguageListAdapter
import com.rahulpatel.newsapp.ui.language.LanguageListViewModel
import com.rahulpatel.newsapp.ui.news.NewsListAdapter
import com.rahulpatel.newsapp.ui.news.NewsListViewModel
import com.rahulpatel.newsapp.ui.offline.OfflineTopHeadlineViewModel
import com.rahulpatel.newsapp.ui.pagination.PaginationTopHeadlineAdapter
import com.rahulpatel.newsapp.ui.pagination.PaginationTopHeadlineViewModel
import com.rahulpatel.newsapp.ui.sources.NewsSourceAdapter
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
            activity, ViewModelProviderFactory(OfflineTopHeadlineViewModel::class) {
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
            activity, ViewModelProviderFactory(PaginationTopHeadlineViewModel::class) {
                PaginationTopHeadlineViewModel(
                    paginationTopHeadlineRepository, dispatcherProvider
                )
            })[PaginationTopHeadlineViewModel::class.java]
    }


    @Provides
    fun provideNewsViewModel(
        newsRepository: NewsRepository,
        logger: Logger,
        networkHelper: NetworkHelper,
        dispatcherProvider: DispatcherProvider
    ): NewsListViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(NewsListViewModel::class) {
            NewsListViewModel(newsRepository, logger, dispatcherProvider, networkHelper)
        })[NewsListViewModel::class.java]
    }

    @Provides
    fun provideNewsSourceAdapter() = NewsSourceAdapter(ArrayList())

    @Provides
    fun provideNewsAdapter() = NewsListAdapter(ArrayList())

    @Provides
    fun provideCountryListViewModel(
        countryListRepository: CountryListRepository,
        dispatcherProvider: DispatcherProvider
    ): CountryListViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(CountryListViewModel::class) {
            CountryListViewModel(countryListRepository, dispatcherProvider)
        })[CountryListViewModel::class.java]
    }

    @Provides
    fun provideCountryListAdapter() = CountryListAdapter(ArrayList())

    @Provides
    fun provideLanguageAdapter() = LanguageListAdapter(ArrayList())

    @Provides
    fun provideLanguageListViewModel(
        languageRepository: LanguageListRepository,
        dispatcherProvider: DispatcherProvider
    ): LanguageListViewModel {
        return ViewModelProvider(
            activity,
            ViewModelProviderFactory(LanguageListViewModel::class) {
                LanguageListViewModel(languageRepository, dispatcherProvider)
            })[LanguageListViewModel::class.java]
    }


}