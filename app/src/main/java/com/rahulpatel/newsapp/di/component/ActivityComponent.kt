package com.rahulpatel.newsapp.di.component

import com.rahulpatel.newsapp.data.model.repository.OfflineTopHeadlineRepository
import com.rahulpatel.newsapp.data.model.repository.PaginationTopHeadlineRepository
import com.rahulpatel.newsapp.data.model.repository.TopHeadlineRepository
import com.rahulpatel.newsapp.di.ActivityScope
import com.rahulpatel.newsapp.di.module.ActivityModule
import com.rahulpatel.newsapp.ui.offline.OfflineTopHeadlineActivity
import com.rahulpatel.newsapp.ui.pagination.PaginationTopHeadlineActivity
import com.rahulpatel.newsapp.ui.topheadline.TopHeadlineActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(activity: TopHeadlineActivity)

    fun inject(activity: OfflineTopHeadlineActivity)

    fun inject(activity: PaginationTopHeadlineActivity)

    fun getTopHeadlineRepository(): TopHeadlineRepository

    fun getOfflineTopHeadlineRepository(): OfflineTopHeadlineRepository

    fun getPaginationTopHeadlineRepository(): PaginationTopHeadlineRepository
}