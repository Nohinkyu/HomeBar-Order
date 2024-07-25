package com.devik.homebarorder.data.source.local

import com.devik.homebarorder.data.source.local.database.CategoryDatabase
import com.devik.homebarorder.data.source.local.database.CategoryEntity
import com.devik.homebarorder.data.source.local.database.MenuDatabase
import javax.inject.Inject

class CategoryLocalDataSource @Inject constructor(
    private val categoryDatabase: CategoryDatabase,
    private val menuDatabase: MenuDatabase
) :
    CategoryDataSource {

    private val categoryDao = categoryDatabase.categoryDao()
    private val menuDao = menuDatabase.menuDao()

    override suspend fun insertCategory(categoryEntity: CategoryEntity) {
        categoryDao.insertCategory(categoryEntity)
    }

    override suspend fun deleteCategory(categoryEntity: CategoryEntity) {
        categoryDao.deleteCategory(categoryEntity)
    }

    override suspend fun getAllCategory(): List<CategoryEntity> {
        return categoryDao.getAllCategory()
    }

    override suspend fun updateCategory(categoryEntity: CategoryEntity) {
        categoryDao.updateCategory(categoryEntity)
    }

    override suspend fun isCategoryExists(category: Int): Boolean {
        return menuDao.isCategoryExists(category)
    }

    override suspend fun deleteAllMenusInCategory(category: Int) {
        menuDao.removeAllMenusInCategory(category)
    }
}