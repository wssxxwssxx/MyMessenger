package com.example.messenger.data.remote.repository

import com.example.messenger.data.vo.UserVo
import com.example.messenger.data.vo.list.UsersListVO
import io.reactivex.Observable

interface UserRepository {
    fun findById(id:Long) : Observable<UserVo>
    fun all(): Observable<UsersListVO>
    fun echoDetails() : Observable<UserVo  >
}