package com.example.messenger.ui.main

import com.example.messenger.ui.base.BaseView
interface MainView : BaseView {

    fun showConversationsLoadError()

    fun showContactsLoadError()

    fun showConversationsScreen()

    fun showContactsScreen()

    fun getContactsFragment(): MainActivity.ContactsFragment

    fun getConversationsFragment(): MainActivity.ConversationsFragment

    fun showNoConversations()

    fun navigateToLogin()

    fun navigateToSettings()
}