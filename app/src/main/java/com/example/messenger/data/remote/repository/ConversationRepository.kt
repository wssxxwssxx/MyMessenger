package com.example.messenger.data.remote.repository

import com.example.messenger.data.vo.ConversationVO
import com.example.messenger.data.vo.list.ConversationListVO
import io.reactivex.Observable

interface ConversationRepository {
    fun findByCoversationById(id : Long): Observable<ConversationVO>
    fun all(): Observable<ConversationListVO>
}