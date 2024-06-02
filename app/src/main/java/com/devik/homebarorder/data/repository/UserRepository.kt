package com.devik.homebarorder.data.repository

import com.devik.homebarorder.data.source.local.UserDataSource

class UserRepository (private val userDataSource: UserDataSource) {

    fun saveUserMailAddress(mailAddress: String) {
        userDataSource.saveUserMail(mailAddress)
    }

    fun getUserMailAddress(): String {
        return userDataSource.getUserMail()
    }

    fun saveUserImageUrl(userImageUrl: String) {
        userDataSource.saveUserProfileImage(userImageUrl)
    }

    fun getUserImageUrl(): String {
        return userDataSource.getUserProfileImage()
    }
}