package com.devik.homebarorder.di

import android.content.Context
import androidx.room.Room
import com.devik.homebarorder.data.source.local.database.MenuDao
import com.devik.homebarorder.data.source.local.database.MenuDatabase
import com.devik.homebarorder.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MenuDatabaseModule {

    @Provides
    @Singleton
    fun provideMenuDatabase(@ApplicationContext context: Context): MenuDatabase {
        return Room.databaseBuilder(
            context,
            MenuDatabase::class.java,
            name = Constants.MENU_DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideMenuDao(menuDatabase: MenuDatabase):MenuDao {
        return menuDatabase.menuDao()
    }
}