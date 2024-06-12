package com.devik.homebarorder.di

import com.devik.homebarorder.data.source.local.CategoryDataSource
import com.devik.homebarorder.data.source.local.CategoryLocalDataSource
import com.devik.homebarorder.data.source.local.database.CategoryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CategoryDataSourceModule {

    @Provides
    fun provideCategoryDataSourceModule(categoryDatabase: CategoryDatabase): CategoryDataSource {
        return CategoryLocalDataSource(categoryDatabase)
    }
}