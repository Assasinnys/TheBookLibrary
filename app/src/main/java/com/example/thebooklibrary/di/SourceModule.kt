package com.example.thebooklibrary.di

import com.example.thebooklibrary.model.datasources.AppPrefSource
import com.example.thebooklibrary.model.datasources.AppPrefSourceImpl
import com.example.thebooklibrary.model.datasources.RemoteSource
import com.example.thebooklibrary.model.datasources.RemoteSourceImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface SourceModule {
    @Binds
    @Singleton
    fun getRemoteSource(source: RemoteSourceImpl): RemoteSource

    @Binds
    @Singleton
    fun getAppPrefSource(source: AppPrefSourceImpl): AppPrefSource
}