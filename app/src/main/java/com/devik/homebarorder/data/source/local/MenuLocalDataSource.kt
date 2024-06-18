package com.devik.homebarorder.data.source.local

import com.devik.homebarorder.data.source.local.database.MenuDatabase
import com.devik.homebarorder.data.source.local.database.MenuEntity
import javax.inject.Inject

class MenuLocalDataSource @Inject constructor(private val menuDatabase: MenuDatabase): MenuDataSource {

    private val dao = menuDatabase.menuDao()

    override suspend fun getAllMenu(): List<MenuEntity> {
        return dao.getAllMenu()
    }

    override suspend fun insertMenu(menuEntity: MenuEntity) {
        dao.insertMenu(menuEntity)
    }

    override suspend fun deleteMenu(menuEntity: MenuEntity) {
        dao.deleteMenu(menuEntity)
    }

    override suspend fun updateMenu(menuEntity: MenuEntity) {
        dao.updateMenu(menuEntity)
    }
}