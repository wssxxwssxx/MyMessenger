package com.example.messenger.ui.login

import android.annotation.SuppressLint
import com.example.messenger.data.AppPreferences
import com.example.messenger.data.remote.request.LoginRequestObject
import com.example.messenger.data.vo.UserVo
import com.example.messenger.service.MessangerApiService
import com.example.messenger.ui.auth.AuthInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginIneractorImpl: LoginInteractor{


    override lateinit var userDetails: UserVo
    override lateinit var accessToken: String
    override lateinit var submittedUsername: String
    override lateinit var submittedPassword: String

    private var service: MessangerApiService = MessangerApiService.getInstance()

    @SuppressLint("CheckResult")
    override fun login(username: String, password: String,
                       listener: AuthInteractor.onAuthFinishedListener) {
        when{
            username.isBlank() -> listener.onUsernameError()
            password.isBlank() -> listener.onPasswordError()
            else -> {
                submittedUsername = username
                submittedPassword = password
                val requestObject = LoginRequestObject(username, password)
                service.login(requestObject)
                    //Обрабатываем запрос в потоке Ввода-ввывода
                    .subscribeOn(Schedulers.io())
                    // Вместе с ним используем этот метод для обработки результата в основном потоке и показать в пользовательском интерфейсе
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ res ->
                        if(res.code() != 403){
                            accessToken = res.headers()["Authorization"] as String
                            listener.onAuthSuccess()
                        } else {
                            listener.onAuthError()
                        }
                    },{ error ->
                        listener.onAuthError()
                        error.printStackTrace()
                    })
            }
        }
    }

    @SuppressLint("CheckResult")
    override fun retrieveDetails(
        preferences: AppPreferences,
        listener: LoginInteractor.OnDetailsRetrievalFinishedListener
    ) {
        service.echoDetails(preferences.accessToken as String)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ res ->
                userDetails = res
                listener.onDetailsRetrievalSucces()
            },{ error ->
                listener.onDetailsRetrievalError()
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