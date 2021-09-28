package com.example.messenger.ui.auth

import com.example.messenger.data.AppPreferences
import com.example.messenger.data.vo.UserVo

interface AuthInteractor {

    var userDetails: UserVo
    var accessToken: String
    var submittedUsername: String
    var submittedPassword: String

    interface onAuthFinishedListener {
        fun onAuthSuccess()
        fun onAuthError()
        fun onUsernameError()
        fun onPasswordError()
    }

    fun persistAccessToken(preferences: AppPreferences)
    fun persistUserDetails(preferences: AppPreferences)
}