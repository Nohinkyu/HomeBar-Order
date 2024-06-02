package com.devik.homebarorder.ui.screen.signin

import androidx.lifecycle.ViewModel
import com.devik.homebarorder.data.repository.UserRepository

class SignInViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun saveUserImageUrl(userImageUrl: String) {
        userRepository.saveUserImageUrl(userImageUrl)
    }

    fun saveUserMailAddress(mailAddress: String) {
        userRepository.saveUserMailAddress(mailAddress)
    }
}