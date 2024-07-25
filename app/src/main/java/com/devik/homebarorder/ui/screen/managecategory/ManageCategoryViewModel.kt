package com.devik.homebarorder.ui.screen.managecategory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devik.homebarorder.data.repository.CategoryRepository
import com.devik.homebarorder.data.source.local.database.CategoryEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageCategoryViewModel @Inject constructor(private val categoryRepository: CategoryRepository) :
    ViewModel() {

    private val _categoryList = MutableStateFlow<List<CategoryEntity>>(emptyList())
    val categoryList: StateFlow<List<CategoryEntity>> = _categoryList

    private val _categoryTextState = MutableStateFlow<String>("")
    val categoryTextState: StateFlow<String> = _categoryTextState

    private val _deleteDialogState = MutableStateFlow<Boolean>(false)
    val deleteDialogState: MutableStateFlow<Boolean> = _deleteDialogState

    private val _deleteTargetCategory =
        MutableStateFlow<CategoryEntity>(CategoryEntity(category = ""))
    val deleteTargetCategory: MutableStateFlow<CategoryEntity> = _deleteTargetCategory

    private val _editCategoryDialogState = MutableStateFlow<Boolean>(false)
    val editCategoryDialogState: MutableStateFlow<Boolean> = _editCategoryDialogState

    private val _editTargetCategory =
        MutableStateFlow<CategoryEntity>(CategoryEntity(category = ""))
    val editTargetCategory: MutableStateFlow<CategoryEntity> = _editTargetCategory

    private val _editTargetCategoryTextState =
        MutableStateFlow<String>("")
    val editTargetCategoryTextState: MutableStateFlow<String> = _editTargetCategoryTextState

    fun getAllCategoryList() {
        viewModelScope.launch {
            _categoryList.value = categoryRepository.getAllCategory()
        }
    }

    fun insertCategory(categoryEntity: CategoryEntity) {
        viewModelScope.launch {
            categoryRepository.insertCategory(categoryEntity)
            getAllCategoryList()
        }
        _categoryTextState.value = ""
    }

    fun onCategoryTextStateChange(text: String) {
        _categoryTextState.value = text
    }

    fun deleteCategory(categoryEntity: CategoryEntity) {
        viewModelScope.launch {
            async { categoryRepository.deleteAllMenusInCategory(categoryEntity.uid) }.await()
            categoryRepository.deleteCategory(categoryEntity)
            getAllCategoryList()
        }
        _deleteTargetCategory.value = CategoryEntity(category = "")
    }

    fun setDeleteTargetCategory(categoryEntity: CategoryEntity) {
        _deleteTargetCategory.value = categoryEntity
    }

    fun onEditCategoryTextChange(text: String) {
        _editTargetCategoryTextState.value = text
    }

    fun updateCategory(categoryEntity: CategoryEntity) {
        viewModelScope.launch {
            categoryRepository.updateCategory(categoryEntity)
        }
        _editTargetCategory.value = CategoryEntity(category = "")
        _editTargetCategoryTextState.value = ""
        _editCategoryDialogState.value = false
    }

    fun showDeleteDialog() {
        _deleteDialogState.value = true
    }

    fun closeDeleteDialog() {
        _deleteDialogState.value = false
    }

    fun setEditTargetCategory(categoryEntity: CategoryEntity) {
        _editTargetCategory.value = categoryEntity
        _editTargetCategoryTextState.value = categoryEntity.category
    }

    fun showEditCategoryDialog() {
        _editCategoryDialogState.value = true
    }

    fun closeEditCategoryDialog() {
        _editCategoryDialogState.value = false
    }
}