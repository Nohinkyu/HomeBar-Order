package com.devik.homebarorder.data.source.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MenuDao {

    @Insert
    suspend fun insertMenu(menuEntity: MenuEntity)

    @Delete
    suspend fun deleteMenu(menuEntity: MenuEntity)

    @Query("SELECT * FROM menu_list")
    suspend fun getAllMenu() : List<MenuEntity>

    @Update
    suspend fun updateMenu(menuEntity: MenuEntity)
}