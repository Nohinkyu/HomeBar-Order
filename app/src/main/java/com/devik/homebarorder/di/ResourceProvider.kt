package com.devik.homebarorder.di

interface ResourceProvider {
    fun getString(resourceId: Int): String
}