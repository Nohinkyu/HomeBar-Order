package com.devik.homebarorder.data.source.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CategoryDao {

    @Insert
    suspend fun insertCategory(categoryEntity: CategoryEntity)

    @Delete
    suspend fun deleteCategory(categoryEntity: CategoryEntity)

    @Query("SELECT * FROM category_list")
    suspend fun getAllCategory(): List<CategoryEntity>

    @Update
    suspend fun updateCategory(categoryEntity: CategoryEntity)
}