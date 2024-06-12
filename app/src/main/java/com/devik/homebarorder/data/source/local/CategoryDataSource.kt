package com.devik.homebarorder.data.source.local

import com.devik.homebarorder.data.source.local.database.CategoryEntity

interface CategoryDataSource {

    suspend fun insertCategory(categoryEntity: CategoryEntity)
    suspend fun deleteCategory(categoryEntity: CategoryEntity)
    suspend fun getAllCategory(): List<CategoryEntity>
    suspend fun updateCategory(categoryEntity: CategoryEntity)
}