package com.devik.homebarorder.data.source.local

import com.devik.homebarorder.data.source.local.database.CategoryDatabase
import com.devik.homebarorder.data.source.local.database.CategoryEntity
import javax.inject.Inject

class CategoryLocalDataSource @Inject constructor(private val database: CategoryDatabase) :
    CategoryDataSource {

    private val dao = database.categoryDao()
    override suspend fun insertCategory(categoryEntity: CategoryEntity) {
        dao.insertCategory(categoryEntity)
    }

    override suspend fun deleteCategory(categoryEntity: CategoryEntity) {
        dao.deleteCategory(categoryEntity)
    }

    override suspend fun getAllCategory(): List<CategoryEntity> {
        return dao.getAllCategory()
    }

    override suspend fun updateCategory(categoryEntity: CategoryEntity) {
        dao.updateCategory(categoryEntity)
    }
}