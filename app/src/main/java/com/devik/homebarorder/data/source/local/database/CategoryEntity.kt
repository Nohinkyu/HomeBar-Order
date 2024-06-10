package com.devik.homebarorder.data.source.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_list")
data class CategoryEntity(
    @PrimaryKey (autoGenerate = true) val uid: Int = 0,
    val category: String,
    val order: Int,
)
