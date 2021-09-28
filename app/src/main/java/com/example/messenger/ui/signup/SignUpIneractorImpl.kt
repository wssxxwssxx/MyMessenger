package com.example.messenger.ui.signup

import android.annotation.SuppressLint
import android.text.TextUtils
import com.example.messenger.data.AppPreferences
import com.example.messenger.data.remote.request.LoginRequestObject
import com.example.messenger.data.remote.request.UserRequestObject
import com.example.messenger.data.vo.UserVo
import com.example.messenger.service.MessangerApiService
import com.example.messenger.ui.auth.AuthInteractor
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SignUpIneractorImpl: SignUpInteractor {

    override lateinit var userDetails: UserVo
    override lateinit var accessToken: String
    override lateinit var submittedUsername: String
    override lateinit var submittedPassword: String

    private var service : MessangerApiService = MessangerApiService.getInstance()

    @SuppressLint("CheckResult")
    override fun signUp(
        username: String,
        password: String,
        phoneNumber: String,
        listener: SignUpInteractor.OnSignUpFinishedListener
    ) {
        submittedUsername = username
        submittedPassword = password
        val requestObject = UserRequestObject(username, password, phoneNumber)
        when{
            TextUtils.isEmpty(username) -> listener.onUsernameError()
            TextUtils.isEmpty(password) -> listener.onPasswordError()
            TextUtils.isEmpty(phoneNumber) -> listener.onPhoneNumberError()
            else -> {
                service.createUser(requestObject)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ res ->
                        userDetails = res
                        listener.onSuccess()
                    },{error ->
                        listener.onError()
                        error.printStackTrace()
                    })
            }
        }
    }

    override fun getAuthorization(listener: AuthInteractor.onAuthFinishedListener) {
        val requestObject = LoginRequestObject(submittedUsername,submittedPassword)
        service.login(requestObject)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({res ->
                accessToken = res.headers()["Authorization"] as String
                listener.onAuthSuccess()
            },{error ->
                listener.onAuthError()
                error.printStackTrace()
            })
    }

    override fun persistAccessToken(preferences: AppPreferences) {
        preferences.storeAccessToken(accessToken)
    }

    override fun persistUserDetails(preferences: AppPreferences) {
        preferences.storeUserDetails(userDetails)
    }
}