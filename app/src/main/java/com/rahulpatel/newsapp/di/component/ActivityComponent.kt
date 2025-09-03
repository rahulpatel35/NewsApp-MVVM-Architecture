package com.rahulpatel.newsapp.di.component

import com.rahulpatel.newsapp.di.ActivityScope
import com.rahulpatel.newsapp.di.module.ActivityModule
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

}