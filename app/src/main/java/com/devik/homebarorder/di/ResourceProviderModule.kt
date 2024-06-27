package com.devik.homebarorder.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ResourceProviderModule {

    @Provides
    fun provideResourceModule(@ApplicationContext context: Context):ResourceProvider {
        return StringResourceProvider(context)
    }
}