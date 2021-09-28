package com.example.messenger.ui.login

import com.example.messenger.data.AppPreferences
import com.example.messenger.ui.auth.AuthInteractor

interface LoginInteractor : AuthInteractor{
    interface OnDetailsRetrievalFinishedListener{
        fun onDetailsRetrievalSucces()
        fun onDetailsRetrievalError()
    }

    fun login(username: String,password: String, listener: AuthInteractor.onAuthFinishedListener)
    fun retrieveDetails(preferences: AppPreferences, listener: OnDetailsRetrievalFinishedListener)
}