package com.example.messenger.ui.signup

import com.example.messenger.ui.auth.AuthView
import com.example.messenger.ui.base.BaseView

interface SignUpView: BaseView,AuthView {
    fun showProgress()
    fun showSignUpError()
    fun setUsernameError()
    fun setPasswordError()
    fun setPhoneNumberError()
    fun hideProgress()
    fun navigateToHome()
}
