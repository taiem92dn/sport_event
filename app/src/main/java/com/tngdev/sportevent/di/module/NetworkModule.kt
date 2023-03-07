package com.tngdev.sportevent.di.module

import android.app.Application
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.tngdev.sportevent.network.INetworkCheckService
import com.tngdev.sportevent.util.Utils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @Named("non_auth_client")
    fun provideNonAuthOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            // use for Stetho
            .addNetworkInterceptor(StethoInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideNetworkCheckService(application: Application): INetworkCheckService {
        return object : INetworkCheckService {
            override fun hasInternet(): Boolean {
                return Utils.hasInternet(application)
            }
        }
    }
}