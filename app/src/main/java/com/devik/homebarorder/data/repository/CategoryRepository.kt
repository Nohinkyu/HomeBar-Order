package com.devik.homebarorder.data.repository

import com.devik.homebarorder.data.source.local.CategoryDataSource
import com.devik.homebarorder.data.source.local.database.CategoryEntity
import javax.inject.Inject

class CategoryRepository @Inject constructor(private val categoryDataSource: CategoryDataSource) {

    suspend fun insertCategory(categoryEntity: CategoryEntity) {
        categoryDataSource.insertCategory(categoryEntity)
    }

    suspend fun deleteCategory(categoryEntity: CategoryEntity) {
        categoryDataSource.deleteCategory(categoryEntity)
    }

    suspend fun getAllCategory(): List<CategoryEntity> {
        return categoryDataSource.getAllCategory()
    }

    suspend fun updateCategory(categoryEntity: CategoryEntity) {
        categoryDataSource.updateCategory(categoryEntity)
    }

    suspend fun deleteAllMenusInCategory(category: Int) {
        categoryDataSource.deleteAllMenusInCategory(category)
    }
}