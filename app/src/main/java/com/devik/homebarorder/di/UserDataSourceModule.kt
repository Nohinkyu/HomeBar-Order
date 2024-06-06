package com.devik.homebarorder.di

import com.devik.homebarorder.data.source.local.PreferenceManager
import com.devik.homebarorder.data.source.local.UserDataSource
import com.devik.homebarorder.data.source.local.UserLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UserDataSourceModule {

    @Provides
    fun provideUserLocalDataSource(preferenceManager: PreferenceManager): UserDataSource {
        return UserLocalDataSource(preferenceManager)
    }
}