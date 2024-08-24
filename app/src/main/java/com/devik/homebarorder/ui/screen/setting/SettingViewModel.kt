package com.devik.homebarorder.ui.screen.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devik.homebarorder.data.source.local.database.PreferenceManager
import com.devik.homebarorder.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.gotrue.Auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val preferenceManager: PreferenceManager,
    private val supabseAuth: Auth
) :
    ViewModel() {

    private val _isImageGridChecked = MutableStateFlow<Boolean>(false)
    val isImageGridChecked: StateFlow<Boolean> = _isImageGridChecked

    private val _isImageListChecked = MutableStateFlow<Boolean>(false)
    val isImageListChecked: StateFlow<Boolean> = _isImageListChecked

    private val _isNoImageListChecked = MutableStateFlow<Boolean>(false)
    val isNoImageListChecked: StateFlow<Boolean> = _isNoImageListChecked

    private val _signOutDialogState = MutableStateFlow<Boolean>(false)
    val signOutDialogState: StateFlow<Boolean> = _signOutDialogState

    private val _isManageMode =
        MutableStateFlow<Boolean>(preferenceManager.getBoolean(Constants.KEY_MANAGE_MODE, true))
    val isManageMode: StateFlow<Boolean> = _isManageMode

    private val _tableNumberState =
        MutableStateFlow<String>(preferenceManager.getString(Constants.KEY_TABLE_NUMBER, "1"))
    val tableNumberState: StateFlow<String> = _tableNumberState

    private val _editTableNumberTextState =
        MutableStateFlow<String>(preferenceManager.getString(Constants.KEY_TABLE_NUMBER, "1"))
    val editTableNumberTextState: MutableStateFlow<String> = _editTableNumberTextState

    private val _editTableNumberDialogState = MutableStateFlow<Boolean>(false)
    val editTableNumberDialogState: StateFlow<Boolean> = _editTableNumberDialogState

    private val _changeManageModeDialogState = MutableStateFlow<Boolean>(false)
    val changeManageModeDialogState: StateFlow<Boolean> = _changeManageModeDialogState

    private val _changeManageModeTextState = MutableStateFlow<String>("")
    val changeManageModeTextState: StateFlow<String> = _changeManageModeTextState

    fun imageGridCheck() {
        preferenceManager.putString(Constants.KEY_MENU_LIST_STATE, Constants.IMAGE_GRID_STATE)
        _isImageGridChecked.value = true
        _isImageListChecked.value = false
        _isNoImageListChecked.value = false
    }

    fun imageListCheck() {
        preferenceManager.putString(Constants.KEY_MENU_LIST_STATE, Constants.IMAGE_LIST_STATE)
        _isImageGridChecked.value = false
        _isImageListChecked.value = true
        _isNoImageListChecked.value = false
    }

    fun noImageListCheck() {
        preferenceManager.putString(Constants.KEY_MENU_LIST_STATE, Constants.NO_IMAGE_LIST_STATE)
        _isImageGridChecked.value = false
        _isImageListChecked.value = false
        _isNoImageListChecked.value = true
    }

    fun checkListState() {
        when (preferenceManager.getString(Constants.KEY_MENU_LIST_STATE, "")) {
            Constants.IMAGE_LIST_STATE -> {
                _isImageListChecked.value = true
            }

            Constants.NO_IMAGE_LIST_STATE -> {
                _isNoImageListChecked.value = true
            }

            else -> {
                _isImageGridChecked.value = true
            }
        }
    }

    fun openSignOutDialog() {
        _signOutDialogState.value = true
    }

    fun closeSignOutDialog() {
        _signOutDialogState.value = false
    }

    fun signOut() {
        viewModelScope.launch {
            supabseAuth.signOut()
            preferenceManager.removeString(Constants.KEY_MAIL_ADDRESS)
            preferenceManager.removeString(Constants.KEY_PROFILE_IMAGE)
        }
    }

    fun openEditTableNumberDialog() {
        if (_isManageMode.value) {
            _editTableNumberDialogState.value = true
        }
    }

    fun closeEditTableNumberDialog() {
        _editTableNumberDialogState.value = false
    }

    fun onEditTableNumberTextChange(text: String) {
        _editTableNumberTextState.value = text
    }

    fun changeTableNumber() {
        preferenceManager.putString(Constants.KEY_TABLE_NUMBER, _editTableNumberTextState.value)
        _tableNumberState.value = preferenceManager.getString(Constants.KEY_TABLE_NUMBER, "1")
        _editTableNumberDialogState.value = false
    }

    fun openChangeManageModeDialog() {
        _changeManageModeDialogState.value = true
    }

    fun closeChangeManageModeDialog() {
        _changeManageModeDialogState.value = false
    }

    fun onEditManageModeTextChange(text: String) {
        _changeManageModeTextState.value = text
    }

    fun changeUnManageMode() {
        preferenceManager.putBoolean(Constants.KEY_MANAGE_MODE, false)
        _isManageMode.value = false
    }

    fun changeManageMode(): Boolean {
        return if (_changeManageModeTextState.value == preferenceManager.getString(
                Constants.KEY_MAIL_ADDRESS,
                ""
            )
        ) {
            preferenceManager.putBoolean(Constants.KEY_MANAGE_MODE, true)
            _isManageMode.value = true
            true
        } else {
            false
        }
    }
}