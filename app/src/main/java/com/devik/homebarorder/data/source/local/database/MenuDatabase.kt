package com.devik.homebarorder.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [MenuEntity::class], version = 1)
@TypeConverters(BitmapTypeConverter::class)
abstract class MenuDatabase: RoomDatabase() {

    abstract fun menuDao(): MenuDao
}