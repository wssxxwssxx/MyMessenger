package com.example.messenger.ui.main

import android.annotation.SuppressLint
import android.content.Context
import com.example.messenger.data.AppPreferences
import com.example.messenger.data.remote.repository.ConversationRepository
import com.example.messenger.data.remote.repository.ConversationRepositoryImpl
import com.example.messenger.data.remote.repository.UserRepository
import com.example.messenger.data.remote.repository.UserRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainInteractorImpl(val context: Context) : MainInteractor {

    private val userRepository: UserRepository = UserRepositoryImpl(context)
    private val conversationRepository: ConversationRepository = ConversationRepositoryImpl(context)

    override fun loadContacts(listener: MainInteractor.OnContactsLoadFinishedListener) {
        /*
         * Load all users registered on the Messenger API platform.
         * These users are contacts that can be communicated with by
         * the currently logged in user.
         */
        userRepository.all()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ res ->
                /*
                 * Contacts were loaded successfully.
                 * onContactsLoadSuccess() is called with the API response
                 * data passed as an argument.
                 */
                listener.onContactsLoadSuccess(res) },
                { error ->
                    /*
                     * Contact load failed hence onContactsLoadError()
                     * is called.
                     */
                    listener.onContactsLoadError()
                    error.printStackTrace()})
    }

    override fun loadConversations(listener: MainInteractor.OnConversationsLoadFinishedListener) {
        /*
         * Retrieve all conversations of the currently logged in user
         * from the Messenger API.
         */
        conversationRepository.all()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ res -> listener.onConversationsLoadSuccess(res) },
                { error ->
                    listener.onConversationsLoadError()
                    error.printStackTrace()})
    }

    override fun logout(listener: MainInteractor.OnLogoutFinishedListener) {
        /*
         * Clear all locally stores data and call listener's
         * onLogoutSuccess() callback
         */
        val preferences: AppPreferences = AppPreferences.create(context)
        preferences.clear()
        listener.onLogoutSuccess()
    }
}