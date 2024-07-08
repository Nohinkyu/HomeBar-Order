package com.devik.homebarorder.ui.screen.managemenu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devik.homebarorder.data.repository.CategoryRepository
import com.devik.homebarorder.data.repository.MenuRepository
import com.devik.homebarorder.data.source.local.database.CategoryEntity
import com.devik.homebarorder.data.source.local.database.MenuEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageMenuViewModel @Inject constructor(
    private val menuRepository: MenuRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _allMenuList = MutableStateFlow<List<MenuEntity>>(emptyList())

    private val _selectedCategoryMenu = MutableStateFlow<List<MenuEntity>>(emptyList())
    val selectedCategoryMenu: StateFlow<List<MenuEntity>> = _selectedCategoryMenu

    private val _allCategoryList = MutableStateFlow<List<CategoryEntity>>(emptyList())
    val allCategoryList: StateFlow<List<CategoryEntity>> = _allCategoryList

    private val _deleteTargetMenu = MutableStateFlow<MenuEntity?>(null)

    private val _dialogState = MutableStateFlow<Boolean>(false)
    val dialogState: StateFlow<Boolean> = _dialogState

    private val _isAllCategorySelected = MutableStateFlow<Boolean>(true)
    val isAllCategorySelected: StateFlow<Boolean> = _isAllCategorySelected

    private val _selectedCategory = MutableStateFlow<CategoryEntity?>(null)
    val selectedCategory: StateFlow<CategoryEntity?> = _selectedCategory

    private val _searchAppBarState = MutableStateFlow<Boolean>(false)
    val searchAppBarState: StateFlow<Boolean> = _searchAppBarState

    private val _searchTextState = MutableStateFlow<String>("")
    val searchTextState: StateFlow<String> = _searchTextState

    fun getAllMenuList() {
        viewModelScope.launch {
            _allMenuList.value = menuRepository.getAllMenu().sortedBy { it.menuPrice }
            _selectedCategoryMenu.value = _allMenuList.value
        }
    }

    fun getAllCategoryList() {
        viewModelScope.launch {
            _allCategoryList.value = categoryRepository.getAllCategory()
        }
    }

    fun getSelectedCategoryMenu(categoryEntity: CategoryEntity) {
        _isAllCategorySelected.value = false
        _selectedCategory.value = categoryEntity
        _selectedCategoryMenu.value = _allMenuList.value.filter { menu ->
            menu.menuCategory == categoryEntity.uid
        }
    }

    fun setDeleteTargetMenu(menuEntity: MenuEntity) {
        _deleteTargetMenu.value = menuEntity
    }

    fun cancelDeleteMenu() {
        _deleteTargetMenu.value = null
    }

    fun allMenuSelect() {
        _isAllCategorySelected.value = true
        _selectedCategory.value = null
        _selectedCategoryMenu.value = _allMenuList.value
    }

    fun showDeleteDialog() {
        _dialogState.value = true
    }

    fun closeDeleteDialog() {
        _dialogState.value = false
    }

    fun openSearchAppBar() {
        _searchAppBarState.value = true
    }

    fun closeSearchAppBar() {
        _searchAppBarState.value = false
        _searchTextState.value = ""
        if (_isAllCategorySelected.value) {
            _selectedCategoryMenu.value = _allMenuList.value
        } else {
            _selectedCategoryMenu.value = _allMenuList.value.filter { menu ->
                menu.menuCategory == _selectedCategory.value?.uid
            }
        }
    }

    fun onSearchTextChange(text: String) {
        viewModelScope.launch {
            _searchTextState.value = text
            searchMenu(_searchTextState.value)
        }
    }

    fun searchMenu(text: String) {
        if (_isAllCategorySelected.value) {
            _selectedCategoryMenu.value = _allMenuList.value.filter { menu ->
                menu.toString().contains(text)
            }
        } else {
            _selectedCategoryMenu.value = _allMenuList.value.filter { menu ->
                menu.menuCategory == _selectedCategory.value?.uid
            }.filter { menu ->
                menu.toString().contains(text)
            }
        }
    }

    fun deleteMenu() {
        viewModelScope.launch {
            if (_deleteTargetMenu.value != null) {
                menuRepository.deleteMenu(_deleteTargetMenu.value!!)
                _selectedCategoryMenu.value =
                    _selectedCategoryMenu.value - _deleteTargetMenu.value!!
                _allMenuList.value = _allMenuList.value - _deleteTargetMenu.value!!
            }
            _deleteTargetMenu.value = null
        }
    }
}