package com.devik.homebarorder.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CategoryEntity::class], version = 1)
abstract class CategoryDatabase : RoomDatabase() {

    abstract fun categoryDao() : CategoryDao
}