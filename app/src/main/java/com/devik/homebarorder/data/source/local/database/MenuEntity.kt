package com.devik.homebarorder.data.source.local.database

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "menu_list")
data class MenuEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    val menuName: String,
    val menuInfo: String = "",
    val menuPrice: Int = 0,
    val menuImage: Bitmap? = null,
    val menuCategory: Int
)
