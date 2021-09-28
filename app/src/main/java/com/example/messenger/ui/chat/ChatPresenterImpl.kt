package com.example.messenger.ui.chat

import android.widget.Toast
import com.example.messenger.data.vo.ConversationVO
import com.example.messenger.utils.message.Message
import java.text.SimpleDateFormat

/**
 * @author Iyanu Adelekan. 29/10/2017.
 */
class ChatPresenterImpl(val view: ChatView) : ChatPresenter, ChatInteractor.OnMessageSendFinishedListener,
        ChatInteractor.onMessageLoadFinishedListener {

    private val interactor: ChatInteractor = ChatInteractorImpl(view.getContext())

    override fun onLoadSuccess(conversationVO: ConversationVO) {
        val adapter = view.getMessageListAdapter() // retrieve the adapter of MessagesList

        val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        conversationVO.messages.forEach { message ->
            adapter.addToStart(Message(message.senderId, message.body,
                    dateFormatter.parse(message.createAt.split(".")[0])), true)
        }
    }

    override fun onLoadError() {
        view.showConversationLoadError()
    }

    override fun onSendSuccess() {
        Toast.makeText(view.getContext(), "Message sent", Toast.LENGTH_LONG).show()
    }

    override fun onSendError() {
        view.showMessageSendError()
    }

    /**
     * Called by [ChatView] to send a message to a user
     * @param recipientId unique id of message recipient
     * @param message message to be sent to recipient
     */
    override fun sendMessage(recipientId: Long, message: String) {
        interactor.sendMessage(recipientId, message,this)
    }

    /**
     * Called by [ChatView] to load the messages in an opened thread
     * @param conversationId unique id of conversation to be loaded
     */
    override fun loadMessages(conversationId: Long) {
        interactor.loadMessages(conversationId, this)
    }
}