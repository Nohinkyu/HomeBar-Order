package com.devik.homebarorder.di

import android.content.Context
import androidx.room.Room
import com.devik.homebarorder.data.source.local.database.CategoryDao
import com.devik.homebarorder.data.source.local.database.CategoryDatabase
import com.devik.homebarorder.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CategoryDatabaseModule {

    @Provides
    @Singleton
    fun provideCategoryDatabase(@ApplicationContext context: Context): CategoryDatabase {
        return Room.databaseBuilder(
            context,
            CategoryDatabase::class.java,
            name = Constants.CATEGORY_DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideCategoryDao(categoryDatabase: CategoryDatabase):CategoryDao {
        return  categoryDatabase.categoryDao()
    }
}