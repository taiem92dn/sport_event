package com.tngdev.sportevent.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.tngdev.sportevent.R
import com.tngdev.sportevent.data.LocalMatchDataSource
import com.tngdev.sportevent.data.LocalMatchDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    @Named("ioDispatcher")
    fun provideIoDispatcher() = Dispatchers.IO

    @Provides
    @Singleton
    fun provideSharePreference(application: Application): SharedPreferences {
        return application.getSharedPreferences(
            application.getString(R.string.app_name),
            Context.MODE_PRIVATE
        )
    }

    @Provides
    @Singleton
    fun provideMatchDataSource(sharedPreferences: SharedPreferences): LocalMatchDataSource {
        return LocalMatchDataSourceImpl(sharedPreferences)
    }
}
