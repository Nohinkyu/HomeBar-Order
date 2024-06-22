package com.devik.homebarorder.ui.screen.menueditorscreen

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devik.homebarorder.R
import com.devik.homebarorder.data.repository.CategoryRepository
import com.devik.homebarorder.data.repository.MenuRepository
import com.devik.homebarorder.data.source.local.database.CategoryEntity
import com.devik.homebarorder.data.source.local.database.MenuEntity
import com.devik.homebarorder.di.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuEditorViewModel @Inject constructor(
    private val menuRepository: MenuRepository,
    private val categoryRepository: CategoryRepository,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val _categoryList = MutableStateFlow<List<CategoryEntity>>(emptyList())
    val categoryList: StateFlow<List<CategoryEntity>> = _categoryList

    private val _menuName = MutableStateFlow<String>("")
    val menuName: StateFlow<String> = _menuName

    private val _menuPrice = MutableStateFlow<String>("")
    val menuPrice: StateFlow<String> = _menuPrice

    private val _menuInfo = MutableStateFlow<String>("")
    val menuInfo: StateFlow<String> = _menuInfo

    private val _menuCategory = MutableStateFlow<CategoryEntity>(CategoryEntity(category = resourceProvider.getString(R.string.button_category)))
    val menuCategory: StateFlow<CategoryEntity> = _menuCategory

    private val _menuImageBitmap = MutableStateFlow<Bitmap?>(null)
    val menuImageBitmap: StateFlow<Bitmap?> = _menuImageBitmap

    private val _isMenuNameCategoryBlank = MutableStateFlow<Boolean>(true)
    val isMenuNameCategoryBlank: StateFlow<Boolean> = _isMenuNameCategoryBlank

    private val _buttonTextState = MutableStateFlow<String>("")
    val buttonTextState: StateFlow<String> = _buttonTextState

    private val _editTargetMenuUid = MutableStateFlow<Int>(0)

    fun getCategoryList() {
        viewModelScope.launch {
            _categoryList.value = categoryRepository.getAllCategory()
        }
    }

    fun setCategory(categoryEntity: CategoryEntity) {
        _menuCategory.value = categoryEntity
    }

    fun setMenuName(menuName: String) {
        _menuName.value = menuName
    }

    fun setMenuInfo(menuInfo: String) {
        _menuInfo.value = menuInfo
    }

    fun setMenuPrice(menuPrice: String) {
        _menuPrice.value = menuPrice
    }

    fun setMenuImageBitmap(bitmap: Bitmap) {
        _menuImageBitmap.value = bitmap
    }

    fun deleteImageBitmap() {
        _menuImageBitmap.value = null
    }

    fun setButtonText(uid: Int?) {
        if (uid != null) {
            _buttonTextState.value = resourceProvider.getString(R.string.button_edit_menu)
        } else {
            _buttonTextState.value = resourceProvider.getString(R.string.button_add_menu)
        }
    }

    fun checkMenuNameCategoryBlank():Boolean {
         return _menuName.value.isNotBlank() && _categoryList.value.contains(_menuCategory.value)
    }

    fun getEditTargetMenu(uid: Int) {
        viewModelScope.launch {
            val editTarget = menuRepository.getEditTargetMenu(uid)
            _menuName.value = editTarget.menuName
            _menuInfo.value = editTarget.menuInto
            _menuPrice.value = editTarget.menuPrice.toString()
            _menuImageBitmap.value = editTarget.menuImage
            _editTargetMenuUid.value = editTarget.uid
            _menuCategory.value =
                _categoryList.value.find { it.uid == editTarget.menuCategory } ?: CategoryEntity(
                    category = resourceProvider.getString(R.string.button_category)
                )
        }
    }

    fun insertMenu() {
        val menuPrice = if (_menuPrice.value.isBlank()) {
            0
        } else {
            _menuPrice.value.toInt()
        }
        viewModelScope.launch {
            if (_menuName.value.isNotBlank() && _categoryList.value.contains(_menuCategory.value)) {
                val menu = MenuEntity(
                    menuName = _menuName.value,
                    menuInto = _menuInfo.value,
                    menuPrice = menuPrice,
                    menuCategory = _menuCategory.value.uid,
                    menuImage = menuImageBitmap.value
                )
                menuRepository.insertMenu(menu)
            } else {
                _isMenuNameCategoryBlank.value = true
            }
        }
    }

    fun updateMenu() {
        val menuPrice = if (_menuPrice.value.isBlank()) {
            0
        } else {
            _menuPrice.value.toInt()
        }
        viewModelScope.launch {
            if (_menuName.value.isNotBlank() && _categoryList.value.contains(_menuCategory.value)) {
                val menu = MenuEntity(
                    uid = _editTargetMenuUid.value,
                    menuName = _menuName.value,
                    menuInto = _menuInfo.value,
                    menuPrice = menuPrice,
                    menuCategory = _menuCategory.value.uid,
                    menuImage = menuImageBitmap.value
                )
                menuRepository.updateMenu(menu)
            } else {
                _isMenuNameCategoryBlank.value = true
            }
        }
    }
}