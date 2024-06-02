package com.devik.homebarorder.data.source.local

import com.devik.homebarorder.util.Constants.KEY_MAIL_ADDRESS
import com.devik.homebarorder.util.Constants.KEY_PROFILE_IMAGE

class UserLocalDataSource(private val preferenceManager: PreferenceManager) : UserDataSource {

    override fun saveUserMail(mailAddress: String) {
        preferenceManager.putString(KEY_MAIL_ADDRESS, mailAddress)
    }

    override fun getUserMail(): String {
        return preferenceManager.getString(KEY_MAIL_ADDRESS, "")
    }

    override fun saveUserProfileImage(imageUrl: String) {
        preferenceManager.putString(KEY_PROFILE_IMAGE, imageUrl)
    }

    override fun getUserProfileImage(): String {
        return preferenceManager.getString(KEY_PROFILE_IMAGE, "")
    }
}