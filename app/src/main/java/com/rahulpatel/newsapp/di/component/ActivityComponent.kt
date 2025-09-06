package com.rahulpatel.newsapp.di.component

import com.rahulpatel.newsapp.di.ActivityScope
import com.rahulpatel.newsapp.di.module.ActivityModule
import com.rahulpatel.newsapp.ui.offline.OfflineTopHeadlineActivity
import com.rahulpatel.newsapp.ui.topheadline.TopHeadlineActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(activity: TopHeadlineActivity)

    fun inject(activity: OfflineTopHeadlineActivity)
}