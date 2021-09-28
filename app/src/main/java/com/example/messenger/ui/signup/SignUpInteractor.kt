package com.example.messenger.ui.signup

import com.example.messenger.ui.auth.AuthInteractor

interface SignUpInteractor : AuthInteractor{
    interface OnSignUpFinishedListener{
        fun onSuccess()
        fun onUsernameError()
        fun onPasswordError()
        fun onPhoneNumberError()
        fun onError()
    }

    fun signUp(username: String,password: String,phoneNumber: String, listener: OnSignUpFinishedListener)

    fun getAuthorization(listener: AuthInteractor.onAuthFinishedListener)
}