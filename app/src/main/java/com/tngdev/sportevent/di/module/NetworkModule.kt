package com.tngdev.sportevent.di.module

import android.app.Application
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.tngdev.sportevent.data.MatchDataSource
import com.tngdev.sportevent.data.RemoteMatchDataSource
import com.tngdev.sportevent.network.INetworkCheckService
import com.tngdev.sportevent.network.MatchService
import com.tngdev.sportevent.util.Utils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideMatchDataSource(matchService: MatchService): MatchDataSource {
        return RemoteMatchDataSource(matchService)
    }

    @Provides
    @Singleton
    fun provideMatchService(@Named("non_auth_retrofit") retrofit: Retrofit): MatchService {
        return retrofit.create(MatchService::class.java)
    }

    @Provides
    @Singleton
    @Named("non_auth_retrofit")
    fun provideNonAuthRetrofit(@Named("non_auth_client") client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://jmde6xvjr4.execute-api.us-east-1.amazonaws.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

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