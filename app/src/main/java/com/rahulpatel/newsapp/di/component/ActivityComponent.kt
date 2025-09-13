package com.rahulpatel.newsapp.di.component

import com.rahulpatel.newsapp.data.repository.CountryListRepository
import com.rahulpatel.newsapp.data.repository.NewsRepository
import com.rahulpatel.newsapp.data.repository.NewsSourceRepository
import com.rahulpatel.newsapp.data.repository.OfflineTopHeadlineRepository
import com.rahulpatel.newsapp.data.repository.PaginationTopHeadlineRepository
import com.rahulpatel.newsapp.data.repository.TopHeadlineRepository
import com.rahulpatel.newsapp.di.ActivityScope
import com.rahulpatel.newsapp.di.module.ActivityModule
import com.rahulpatel.newsapp.ui.country.CountryListActivity
import com.rahulpatel.newsapp.ui.news.NewsListActivity
import com.rahulpatel.newsapp.ui.offline.OfflineTopHeadlineActivity
import com.rahulpatel.newsapp.ui.pagination.PaginationTopHeadlineActivity
import com.rahulpatel.newsapp.ui.sources.NewsSourcesActivity
import com.rahulpatel.newsapp.ui.topheadline.TopHeadlineActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(activity: TopHeadlineActivity)

    fun inject(activity: OfflineTopHeadlineActivity)

    fun inject(activity: PaginationTopHeadlineActivity)

    fun inject(activity: NewsListActivity)

    fun inject(activity: NewsSourcesActivity)

    fun inject(activity: CountryListActivity)

    fun getTopHeadlineRepository(): TopHeadlineRepository

    fun getOfflineTopHeadlineRepository(): OfflineTopHeadlineRepository

    fun getPaginationTopHeadlineRepository(): PaginationTopHeadlineRepository

    fun getNewsSourceRepository(): NewsSourceRepository

    fun getNewsRepository(): NewsRepository

    fun getCountryListRepository(): CountryListRepository

}