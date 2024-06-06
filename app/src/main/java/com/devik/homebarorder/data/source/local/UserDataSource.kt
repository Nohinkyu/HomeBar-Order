package com.devik.homebarorder.data.source.local

interface UserDataSource {

    fun saveUserMail(mailAddress: String)
    fun getUserMail(): String
    fun saveUserProfileImage(imageUrl: String)
    fun getUserProfileImage(): String
    fun saveIsFirstSignIn()
    fun getIsFirstSignIn() :Boolean
}