package com.devik.homebarorder.di

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StringResourceProvider @Inject constructor(@ApplicationContext context: Context) : ResourceProvider {

    private val appContext = context
    override fun getString(resourceId: Int): String {
        return appContext.getString(resourceId)
    }
}