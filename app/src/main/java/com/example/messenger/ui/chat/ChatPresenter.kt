package com.example.messenger.ui.chat

/**
 * @author Iyanu Adelekan. 29/10/2017.
 */
interface ChatPresenter {

    fun sendMessage(recipientId: Long, message: String)

    fun loadMessages(conversationId: Long)
}