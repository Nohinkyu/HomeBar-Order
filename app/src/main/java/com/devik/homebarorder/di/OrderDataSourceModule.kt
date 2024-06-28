package com.devik.homebarorder.di

import com.devik.homebarorder.data.source.local.database.PreferenceManager
import com.devik.homebarorder.data.source.remote.OrderDataSource
import com.devik.homebarorder.data.source.remote.OrderRemoteDataSource
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.postgrest.Postgrest

@Module
@InstallIn(SingletonComponent::class)
object OrderDataSourceModule {

    @Provides
    fun provideMenuDataSourceModule(
        postgrest: Postgrest,
        moshi: Moshi,
        preferenceManager: PreferenceManager
    ): OrderDataSource {
        return OrderRemoteDataSource(postgrest, moshi, preferenceManager)
    }
}