package com.rahulpatel.newsapp.di.component

import android.content.Context
import com.rahulpatel.newsapp.di.ApplicationContext
import com.rahulpatel.newsapp.di.module.ApplicationModule
import com.rahulpatel.newsapp.ui.NewsApplication
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: NewsApplication)

    @ApplicationContext
    fun getApplicationContext(): Context


}