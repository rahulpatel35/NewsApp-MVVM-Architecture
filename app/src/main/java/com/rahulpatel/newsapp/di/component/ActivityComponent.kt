package com.rahulpatel.newsapp.di.component

import com.rahulpatel.newsapp.di.ActivityScope
import com.rahulpatel.newsapp.di.module.ActivityModule
import com.rahulpatel.newsapp.ui.topheadlines.TopHeadlineActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(activity: TopHeadlineActivity)
}