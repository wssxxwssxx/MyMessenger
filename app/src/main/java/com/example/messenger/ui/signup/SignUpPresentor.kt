package com.example.messenger.ui.signup

import com.example.messenger.data.AppPreferences

interface SignUpPresentor {
    var appPreferences: AppPreferences
    fun executeSignUp(username: String, password: String, phoneNumber: String)
}