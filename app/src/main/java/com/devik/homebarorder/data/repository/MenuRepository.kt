package com.devik.homebarorder.data.repository

import com.devik.homebarorder.data.source.local.MenuDataSource
import com.devik.homebarorder.data.source.local.database.MenuEntity
import javax.inject.Inject

class MenuRepository @Inject constructor(private val menuDatasource: MenuDataSource) {

    suspend fun getAllMenu(): List<MenuEntity> {
        return menuDatasource.getAllMenu()
    }

    suspend fun insertMenu(menuEntity: MenuEntity) {
        menuDatasource.insertMenu(menuEntity)
    }

    suspend fun deleteMenu(menuEntity: MenuEntity) {
        menuDatasource.deleteMenu(menuEntity)
    }

    suspend fun updateMenu(menuEntity: MenuEntity) {
        menuDatasource.updateMenu(menuEntity)
    }
}