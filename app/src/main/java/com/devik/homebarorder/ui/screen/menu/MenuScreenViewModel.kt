package com.devik.homebarorder.ui.screen.menu

import androidx.lifecycle.ViewModel
import com.devik.homebarorder.data.source.local.database.PreferenceManager
import com.devik.homebarorder.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MenuScreenViewModel @Inject constructor(
    private val preferenceManager: PreferenceManager
) :
    ViewModel() {

    private val _isFirstSignIn =
        MutableStateFlow<Boolean>(preferenceManager.getBoolean(Constants.KEY_FIRST_SIGN_IN, true))
    val isFirstSignIn: StateFlow<Boolean> = _isFirstSignIn

    private val _manualDialogState = MutableStateFlow<Boolean>(false)
    val manualDialogState: StateFlow<Boolean> = _manualDialogState

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
}