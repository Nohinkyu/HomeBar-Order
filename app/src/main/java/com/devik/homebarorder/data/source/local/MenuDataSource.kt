package com.devik.homebarorder.data.source.local

import com.devik.homebarorder.data.source.local.database.MenuEntity

interface MenuDataSource {

    suspend fun getAllMenu() :List<MenuEntity>
    suspend fun insertMenu(menuEntity: MenuEntity)
    suspend fun deleteMenu(menuEntity: MenuEntity)
    suspend fun updateMenu(menuEntity: MenuEntity)
    suspend fun getEditTargetMenu(uid: Int): MenuEntity
}