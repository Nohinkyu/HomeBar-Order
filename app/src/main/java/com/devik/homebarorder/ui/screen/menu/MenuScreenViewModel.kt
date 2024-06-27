package com.devik.homebarorder.ui.screen.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devik.homebarorder.data.model.CartMenuItem
import com.devik.homebarorder.data.repository.CategoryRepository
import com.devik.homebarorder.data.repository.MenuRepository
import com.devik.homebarorder.data.repository.RemoteOrderRepository
import com.devik.homebarorder.data.source.local.database.CategoryEntity
import com.devik.homebarorder.data.source.local.database.MenuEntity
import com.devik.homebarorder.data.source.local.database.PreferenceManager
import com.devik.homebarorder.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuScreenViewModel @Inject constructor(
    private val preferenceManager: PreferenceManager,
    private val categoryRepository: CategoryRepository,
    private val menuRepository: MenuRepository,
    private val remoteOrderRepository: RemoteOrderRepository,
) :
    ViewModel() {

    private val _isFirstSignIn =
        MutableStateFlow<Boolean>(preferenceManager.getBoolean(Constants.KEY_FIRST_SIGN_IN, true))
    val isFirstSignIn: StateFlow<Boolean> = _isFirstSignIn

    private val _manualDialogState = MutableStateFlow<Boolean>(false)
    val manualDialogState: StateFlow<Boolean> = _manualDialogState

    private val _allCategoryList = MutableStateFlow<List<CategoryEntity>>(emptyList())
    val allCategoryList: StateFlow<List<CategoryEntity>> = _allCategoryList

    private val _allMenuList = MutableStateFlow<List<MenuEntity>>(emptyList())

    private val _selectedCategory = MutableStateFlow<CategoryEntity?>(null)
    val selectedCategory: StateFlow<CategoryEntity?> = _selectedCategory

    private val _selectedCategoryMenu = MutableStateFlow<List<MenuEntity>>(emptyList())
    val selectedCategoryMenu: StateFlow<List<MenuEntity>> = _selectedCategoryMenu

    private val _cartList = MutableStateFlow<List<CartMenuItem>>(emptyList())
    val cartList: StateFlow<List<CartMenuItem>> = _cartList

    private val _addCartDialogState = MutableStateFlow<Boolean>(false)
    val addCartDialogState: StateFlow<Boolean> = _addCartDialogState

    private val _addTargetCartMenu = MutableStateFlow<MenuEntity?>(null)
    val addTargetCartMenu: StateFlow<MenuEntity?> = _addTargetCartMenu

    private val _addTargetCartMenuCount = MutableStateFlow<Int>(1)
    val addTargetCartMenuCount: StateFlow<Int> = _addTargetCartMenuCount

    private val _allCartPrice = MutableStateFlow<Int>(0)
    val allCartPrice: StateFlow<Int> = _allCartPrice

    private val _allCartCount = MutableStateFlow<Int>(0)
    val allCartCount: StateFlow<Int> = _allCartCount

    private val _orderListDialogState = MutableStateFlow<Boolean>(false)
    val orderListDialogState: StateFlow<Boolean> = _orderListDialogState

    private val _isOrderInProgress = MutableStateFlow<Boolean>(false)
    val isOrderInProgress: StateFlow<Boolean> = _isOrderInProgress

    private val _isOrderSuccess = MutableStateFlow<Boolean>(false)
    val isOrderSuccess: StateFlow<Boolean> = _isOrderSuccess

    private val _isOrderFail = MutableStateFlow<Boolean>(false)
    val isOrderFail: StateFlow<Boolean> = _isOrderFail

    init {
        viewModelScope.launch {
            _cartList.collect { cartList ->
                val totalCount = cartList.sumOf { it.menuCount }
                val totalPrice = cartList.sumOf { it.menuPrice * it.menuCount }
                _allCartPrice.value = totalPrice
                _allCartCount.value = totalCount
            }
        }
    }

    fun checkIsFirstSignIn() {
        if (_isFirstSignIn.value) {
            _manualDialogState.value = true
            _isFirstSignIn.value = false
            preferenceManager.putBoolean(Constants.KEY_FIRST_SIGN_IN, false)
        }
    }

    fun manualDialogDismissRequest() {
        _manualDialogState.value = false
    }

    fun getAllCategoryList() {
        viewModelScope.launch {
            _allCategoryList.value = categoryRepository.getAllCategory()
            _selectedCategory.value = _allCategoryList.value.first()
        }
    }

    fun getAllMenuList() {
        viewModelScope.launch {
            _allMenuList.value =
                menuRepository.getAllMenu().sortedWith(compareBy({ it.menuPrice }, { it.menuName }))
            _selectedCategoryMenu.value =
                _allMenuList.value.filter { it.menuCategory == _selectedCategory.value?.uid }
        }
    }

    fun selectCategory(category: CategoryEntity) {
        _selectedCategory.value = category
        _selectedCategoryMenu.value = _allMenuList.value.filter { it.menuCategory == category.uid }
    }

    fun openAddCartDialog(menuEntity: MenuEntity) {
        _addTargetCartMenu.value = menuEntity
        _addCartDialogState.value = true
    }

    fun closeAddCartDialog() {
        _addCartDialogState.value = false
        _addTargetCartMenu.value = null
        _addTargetCartMenuCount.value = 1
    }

    fun plusTargetMenuCount() {
        _addTargetCartMenuCount.value = _addTargetCartMenuCount.value + 1
    }

    fun minusTargetMenuCount() {
        if (_addTargetCartMenuCount.value > 1) {
            _addTargetCartMenuCount.value = _addTargetCartMenuCount.value - 1
        }
    }

    fun addCart() {
        if (_addTargetCartMenu.value != null &&
            _cartList.value.any { it.menuId == _addTargetCartMenu.value?.uid }
        ) {
            _cartList.value = _cartList.value.map { menu ->
                if (menu.menuId == _addTargetCartMenu.value?.uid) {
                    menu.copy(menuCount = menu.menuCount + _addTargetCartMenuCount.value)
                } else {
                    menu
                }
            }
        } else {
            _cartList.value = _cartList.value + CartMenuItem(
                menuId = _addTargetCartMenu.value!!.uid,
                menuPrice = _addTargetCartMenu.value!!.menuPrice,
                menuName = _addTargetCartMenu.value!!.menuName,
                menuCount = _addTargetCartMenuCount.value
            )
        }
    }

    fun deleteCartMenu(cartMenuItem: CartMenuItem) {
        _cartList.value = _cartList.value - cartMenuItem
    }

    fun plusMenuCount(cartMenuItem: CartMenuItem) {
        _cartList.value = _cartList.value.map { menu ->
            if (menu.menuId == cartMenuItem.menuId) {
                menu.copy(menuCount = menu.menuCount + 1)
            } else {
                menu
            }
        }
    }

    fun minusMenuCount(cartMenuItem: CartMenuItem) {
        _cartList.value = _cartList.value.map { menu ->
            if (menu.menuId == cartMenuItem.menuId && menu.menuCount > 1) {
                menu.copy(menuCount = menu.menuCount - 1)
            } else {
                menu
            }
        }
    }

    fun clearCart() {
        _cartList.value = emptyList()
    }

    fun openOrderListDialog() {
        _orderListDialogState.value = true
    }

    fun closeOrderListDialog() {
        _orderListDialogState.value = false
    }

    fun insertOrder() {
        viewModelScope.launch {
            _isOrderInProgress.value = true
            val result = async { remoteOrderRepository.insertOrder(_cartList.value) }.await()
            _isOrderInProgress.value = false
            if (result) {
                _isOrderSuccess.value = true
                _cartList.value = emptyList()
            } else {
                _isOrderFail.value = true
            }
        }
    }

    fun closeOrderSuccessDialog() {
        _isOrderSuccess.value = false
    }

    fun closeOrderFailDialog() {
        _isOrderFail.value = false
    }
}
