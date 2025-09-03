package com.rahulpatel.newsapp.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.rahulpatel.newsapp.data.model.repository.TopHeadlineRepository
import com.rahulpatel.newsapp.di.ActivityContext
import com.rahulpatel.newsapp.ui.base.ViewModelProviderFactory
import com.rahulpatel.newsapp.ui.topheadlines.TopHeadlineAdapter
import com.rahulpatel.newsapp.ui.topheadlines.TopHeadlineViewModel
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

}