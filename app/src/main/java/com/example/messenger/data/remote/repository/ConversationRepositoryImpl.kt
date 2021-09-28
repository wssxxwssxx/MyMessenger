package com.example.messenger.data.remote.repository

import android.content.Context
import com.example.messenger.data.AppPreferences
import com.example.messenger.data.vo.ConversationVO
import com.example.messenger.data.vo.list.ConversationListVO
import com.example.messenger.service.MessangerApiService
import io.reactivex.Observable

class ConversationRepositoryImpl(ctx: Context) : ConversationRepository{
    private val appPerferences: AppPreferences = AppPreferences.create(ctx)
    private val messengerApiService : MessangerApiService = MessangerApiService.getInstance()

    override fun findByCoversationById(id: Long): Observable<ConversationVO> {
        return messengerApiService.showConversation(id,appPerferences.accessToken as String)
    }

    override fun all(): Observable<ConversationListVO> {
        return messengerApiService.listConversation(appPerferences.accessToken as String)
    }
}