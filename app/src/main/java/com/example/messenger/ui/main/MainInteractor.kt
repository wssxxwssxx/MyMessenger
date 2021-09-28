package com.example.messenger.ui.main

import com.example.messenger.data.vo.list.ConversationListVO
import com.example.messenger.data.vo.list.UsersListVO

interface MainInteractor {

    interface OnConversationsLoadFinishedListener {
        fun onConversationsLoadSuccess(conversationsListVo: ConversationListVO)

        fun onConversationsLoadError()
    }

    interface OnContactsLoadFinishedListener {
        fun onContactsLoadSuccess(userListVO: UsersListVO)

        fun onContactsLoadError()
    }

    interface OnLogoutFinishedListener {
        fun onLogoutSuccess()
    }

    fun loadContacts(listener: MainInteractor.OnContactsLoadFinishedListener)

    fun loadConversations(listener: MainInteractor.OnConversationsLoadFinishedListener)

    fun logout(listener: MainInteractor.OnLogoutFinishedListener)
}