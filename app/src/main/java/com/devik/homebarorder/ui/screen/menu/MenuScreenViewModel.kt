package com.devik.homebarorder.ui.screen.menu

import androidx.lifecycle.ViewModel
import com.devik.homebarorder.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MenuScreenViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    private val _isFirstSignIn = MutableStateFlow<Boolean>(userRepository.getIsFirstSignIn())
    val isFirstSignIn: StateFlow<Boolean> = _isFirstSignIn

    private val _manualDialogState = MutableStateFlow<Boolean>(false)
    val manualDialogState:StateFlow<Boolean> = _manualDialogState

    fun checkIsFirstSignIn() {
        if(_isFirstSignIn.value)  {
            _manualDialogState.value = true
            _isFirstSignIn.value = false
            userRepository.saveIsFirstSignIn()
        }
    }

    fun manualDialogDismissRequest() {
        _manualDialogState.value = false
    }
}