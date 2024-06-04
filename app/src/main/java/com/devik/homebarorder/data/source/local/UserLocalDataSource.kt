package com.devik.homebarorder.data.source.local

import com.devik.homebarorder.util.Constants
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(private val preferenceManager: PreferenceManager) :
    UserDataSource {

    override fun saveUserMail(mailAddress: String) {
        preferenceManager.putString(Constants.KEY_MAIL_ADDRESS, mailAddress)
    }

    override fun getUserMail(): String {
        return preferenceManager.getString(Constants.KEY_MAIL_ADDRESS, "")
    }

    override fun saveUserProfileImage(imageUrl: String) {
        preferenceManager.putString(Constants.KEY_PROFILE_IMAGE, imageUrl)
    }

    override fun getUserProfileImage(): String {
        return preferenceManager.getString(Constants.KEY_PROFILE_IMAGE, "")
    }
}