package com.rahulpatel.newsapp

import android.app.Application
import com.rahulpatel.newsapp.di.component.ApplicationComponent
import com.rahulpatel.newsapp.di.component.DaggerApplicationComponent
import com.rahulpatel.newsapp.di.module.ApplicationModule


//NewsApplication is your app’s entry point before any Activity,
//used for app-wide setup (dependency injection, singletons, logging, etc.).
class NewsApplication : Application() {
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
    }

    private fun injectDependencies() {
        applicationComponent =
            DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()
        applicationComponent.inject(this)
    }
}