package com.rahulpatel.newsapp.di.module

import android.app.Application
import android.content.Context
import com.rahulpatel.newsapp.NewsApplication
import com.rahulpatel.newsapp.data.api.ApiKeyInterceptor
import com.rahulpatel.newsapp.data.api.NetworkService
import com.rahulpatel.newsapp.di.ApplicationContext
import com.rahulpatel.newsapp.di.BaseUrl
import com.rahulpatel.newsapp.di.NetworkAPIKey
import com.rahulpatel.newsapp.utils.AppConstant
import com.rahulpatel.newsapp.utils.DefaultDispatcherProvider
import com.rahulpatel.newsapp.utils.DispatcherProvider
import com.rahulpatel.newsapp.utils.NetworkHelper
import com.rahulpatel.newsapp.utils.NetworkHelperImpl
import com.rahulpatel.newsapp.utils.logger.AppLogger
import com.rahulpatel.newsapp.utils.logger.Logger
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(private val newsApplication: NewsApplication) {

    @ApplicationContext
    @Provides
    fun provideContext(): Context {
        return newsApplication
    }

    @Provides
    @Singleton
    fun provideApplication(): Application = newsApplication

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideNetworkService(
        @BaseUrl baseUrl: String,
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): NetworkService {
        return Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient)
            .addConverterFactory(gsonConverterFactory).build().create(NetworkService::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(apiKeyInterceptor: ApiKeyInterceptor): OkHttpClient =
        OkHttpClient().newBuilder().addInterceptor(apiKeyInterceptor).build()


    @Provides
    @Singleton
    fun provideNetworkHelper(@ApplicationContext context: Context): NetworkHelper {
        return NetworkHelperImpl(context)
    }

    @Provides
    @Singleton
    fun provideApiKeyInterceptor(@NetworkAPIKey apikey: String): ApiKeyInterceptor =
        ApiKeyInterceptor(apikey)

    @Provides
    @Singleton
    fun provideDispatcher(): DispatcherProvider = DefaultDispatcherProvider()

    @Provides
    @Singleton
    fun provideLogger(): Logger = AppLogger()

    @BaseUrl
    @Provides
    fun provideBaseUrl(): String = AppConstant.BASE_URL


    @NetworkAPIKey
    @Provides
    fun provideApiKey(): String = AppConstant.API_KEY
}