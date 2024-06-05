package com.devik.homebarorder.ui.screen.signin

import androidx.lifecycle.ViewModel
import com.devik.homebarorder.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    private val _isSignIn = MutableStateFlow<Boolean>(false)
    val isSignIn: StateFlow<Boolean> = _isSignIn

    fun saveUserImageUrl(userImageUrl: String) {
        userRepository.saveUserImageUrl(userImageUrl)
    }

    fun saveUserMailAddress(mailAddress: String) {
        userRepository.saveUserMailAddress(mailAddress)
    }

    fun checkUserInfo() {
        if (userRepository.getUserMailAddress().isNotBlank() && userRepository.getUserImageUrl()
                .isNotBlank()
        ) {
            _isSignIn.value = true
        }
    }
}