package com.example.messenger.ui.signup

import com.example.messenger.data.AppPreferences
import com.example.messenger.ui.auth.AuthInteractor

class SignUpPresentorImpl(private val view: SignUpView) :
    SignUpPresentor,
    SignUpInteractor.OnSignUpFinishedListener,
    AuthInteractor.onAuthFinishedListener {

    override lateinit var appPreferences: AppPreferences
    private val interactor: SignUpInteractor = SignUpIneractorImpl()

    override fun executeSignUp(username: String, password: String, phoneNumber: String) {
        interactor.signUp(username,password,phoneNumber,this)
    }

    override fun onAuthSuccess() {
        interactor.persistAccessToken(appPreferences)
        interactor.persistUserDetails(appPreferences)
        view.hideProgress()
        view.navigateToHome()
    }

    override fun onAuthError() {
        view.hideProgress()
        view.showAuthError()
    }

    override fun onSuccess() {
        interactor.getAuthorization(this)
    }

    override fun onUsernameError() {
        view.hideProgress()
        view.setUsernameError()
    }

    override fun onPhoneNumberError() {
        view.hideProgress()
        view.setPhoneNumberError()
    }

    override fun onError() {
        view.hideProgress()
        view.showSignUpError()
    }

    override fun onPasswordError() {
        view.hideProgress()
        view.setPasswordError()
    }


}