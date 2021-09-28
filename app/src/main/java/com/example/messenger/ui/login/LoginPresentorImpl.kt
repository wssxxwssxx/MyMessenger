package com.example.messenger.ui.login

import com.example.messenger.data.AppPreferences
import com.example.messenger.ui.auth.AuthInteractor

class LoginPresentorImpl(private val view: LoginView): LoginPresentor,AuthInteractor.onAuthFinishedListener,
LoginInteractor.OnDetailsRetrievalFinishedListener{

    private val interactor: LoginInteractor = LoginIneractorImpl()
    private val preferences: AppPreferences = AppPreferences.create(view.getContext())

    override fun executeLogin(username: String, password: String) {
        view.showProgress()
        interactor.login(username,password,this)
    }

    override fun onAuthSuccess() {
        interactor.persistAccessToken(preferences)
        interactor.retrieveDetails(preferences,this)
    }

    override fun onAuthError() {
        view.showAuthError()
        view.hideProgress()
    }

    override fun onUsernameError() {
        view.hideProgress()
        view.setUsernameError()
    }

    override fun onPasswordError() {
        view.hideProgress()
        view.setPasswordError()
    }

    override fun onDetailsRetrievalSucces() {
        interactor.persistUserDetails(preferences)
        view.hideProgress()
        view.navigateToHome()
    }

    override fun onDetailsRetrievalError() {
        interactor.retrieveDetails(preferences,this)
    }
}