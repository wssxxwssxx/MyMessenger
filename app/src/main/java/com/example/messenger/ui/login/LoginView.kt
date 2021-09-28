package com.example.messenger.ui.login

import com.example.messenger.ui.auth.AuthView
import com.example.messenger.ui.base.BaseView

interface LoginView : BaseView, AuthView {
    fun showProgress()
    fun hideProgress()
    fun setUsernameError()
    fun setPasswordError()
    fun navigateToSignUp()
    fun navigateToHome()
}