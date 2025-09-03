package com.rahulpatel.newsapp.di.component

import android.content.Context
import com.rahulpatel.newsapp.NewsApplication
import com.rahulpatel.newsapp.data.api.NetworkService
import com.rahulpatel.newsapp.di.ApplicationContext
import com.rahulpatel.newsapp.di.module.ApplicationModule
import com.rahulpatel.newsapp.utils.DispatcherProvider
import com.rahulpatel.newsapp.utils.NetworkHelper
import com.rahulpatel.newsapp.utils.logger.Logger
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: NewsApplication)


    @ApplicationContext
    fun getApplicationContext(): Context

    fun getNetworkService(): NetworkService

    fun getNetworkHelper(): NetworkHelper

    fun getDispatcherProvider(): DispatcherProvider

    fun getLoggerProvider(): Logger

}