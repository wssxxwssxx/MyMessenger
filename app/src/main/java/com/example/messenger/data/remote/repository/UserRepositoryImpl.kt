package com.example.messenger.data.remote.repository

import android.content.Context
import com.example.messenger.data.AppPreferences
import com.example.messenger.data.vo.UserVo
import com.example.messenger.data.vo.list.UsersListVO
import com.example.messenger.service.MessangerApiService
import io.reactivex.Observable

class UserRepositoryImpl(ctx: Context) : UserRepository {

    private val preferences : AppPreferences = AppPreferences.create(ctx)
    private val messangerApi: MessangerApiService = MessangerApiService.getInstance()

    override fun findById(id: Long): Observable<UserVo> {
       return messangerApi.showUser(id,preferences.accessToken as String)
    }

    override fun all(): Observable<UsersListVO> {
        return messangerApi.listUsers(preferences.accessToken as String)
    }

    override fun echoDetails(): Observable<UserVo> {
        return messangerApi.echoDetails(preferences.accessToken as String)
    }

}