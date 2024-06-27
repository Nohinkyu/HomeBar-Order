package com.devik.homebarorder.di

import com.devik.homebarorder.data.source.local.MenuDataSource
import com.devik.homebarorder.data.source.local.MenuLocalDataSource
import com.devik.homebarorder.data.source.local.database.MenuDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MenuDataSourceModule {

    @Provides
    fun provideMenuDataSourceModule(menuDatabase: MenuDatabase): MenuDataSource{
        return MenuLocalDataSource(menuDatabase)
    }
}