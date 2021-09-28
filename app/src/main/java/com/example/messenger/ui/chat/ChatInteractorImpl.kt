package com.example.messenger.ui.chat

import android.content.Context
import com.example.messenger.data.AppPreferences
import com.example.messenger.data.remote.repository.ConversationRepository
import com.example.messenger.data.remote.repository.ConversationRepositoryImpl
import com.example.messenger.data.remote.request.MessageRequestObject
import com.example.messenger.service.MessangerApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author Iyanu Adelekan. 29/10/2017.
 */
class ChatInteractorImpl(context: Context) : ChatInteractor {

    private val preferences: AppPreferences = AppPreferences.create(context)
    private val service: MessangerApiService = MessangerApiService.getInstance()
    private val conversationsRepository: ConversationRepository = ConversationRepositoryImpl(context)


    override fun loadMessages(conversationId: Long, listener: ChatInteractor.onMessageLoadFinishedListener) {
        conversationsRepository.findByCoversationById(conversationId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ res -> listener.onLoadSuccess(res)},
                        { error ->
                            listener.onLoadError()
                            error.printStackTrace()})
    }

    override fun sendMessage(recipientId: Long, message: String,
                             listener: ChatInteractor.OnMessageSendFinishedListener) {
        service.createMessage(MessageRequestObject(recipientId, message), preferences.accessToken as String)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ _ -> listener.onSendSuccess()},
                        { error ->
                            listener.onSendError()
                            error.printStackTrace()})
    }
}