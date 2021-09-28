package com.example.messenger.ui.chat

import com.example.messenger.data.vo.ConversationVO

/**
 * @author Iyanu Adelekan. 29/10/2017.
 */
interface ChatInteractor {

    interface OnMessageSendFinishedListener {
        fun onSendSuccess()

        fun onSendError()
    }

    interface onMessageLoadFinishedListener {
        fun onLoadSuccess(conversationVO: ConversationVO)

        fun onLoadError()
    }

    fun sendMessage(recipientId: Long, message: String, listener: OnMessageSendFinishedListener)

    fun loadMessages(conversationId: Long, listener: onMessageLoadFinishedListener)
}