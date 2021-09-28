package com.example.messenger.ui.chat

import com.example.messenger.ui.base.BaseView
import com.example.messenger.utils.message.Message
import com.stfalcon.chatkit.messages.MessagesListAdapter

/**
 * @author Iyanu Adelekan. 29/10/2017.
 */
interface ChatView : BaseView {

    interface ChatAdapter {
        fun navigateToChat(recipientName: String, recipientId: Long, conversationId: Long? = null)
    }

    fun showConversationLoadError()

    fun showMessageSendError()

    fun getMessageListAdapter(): MessagesListAdapter<Message>
}