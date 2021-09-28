package com.example.messenger.ui.main

import com.example.messenger.data.vo.list.ConversationListVO
import com.example.messenger.data.vo.list.UsersListVO

class MainPresenterImpl(val view: MainView) : MainPresenter, MainInteractor.OnConversationsLoadFinishedListener,
    MainInteractor.OnContactsLoadFinishedListener, MainInteractor.OnLogoutFinishedListener {

    private val interactor: MainInteractor = MainInteractorImpl(view.getContext())

    override fun onConversationsLoadSuccess(conversationsListVo: ConversationListVO) {

        if (!conversationsListVo.conversations.isEmpty()) {
            val conversationsFragment = view.getConversationsFragment()
            val conversations = conversationsFragment.conversations
            val adapter = conversationsFragment.conversationsAdapter

            conversations.clear()
            adapter.notifyDataSetChanged()

            conversationsListVo.conversations.forEach { contact ->
                conversations.add(contact)
                adapter.notifyItemInserted(conversations.size - 1)
            }
        } else {
            view.showNoConversations()
        }
    }

    override fun onConversationsLoadError() {
        view.showConversationsLoadError()
    }

    override fun onContactsLoadSuccess(userListVO: UsersListVO) {
        val contactsFragment = view.getContactsFragment()
        val contacts = contactsFragment.contacts
        val adapter = contactsFragment.contactsAdapter

        contacts.clear()
        adapter.notifyDataSetChanged()

        userListVO.users.forEach { contact ->
            contacts.add(contact)
            contactsFragment.contactsAdapter.notifyItemInserted(contacts.size - 1)
        }
    }

    override fun onContactsLoadError() {
        view.showContactsLoadError()
    }

    override fun onLogoutSuccess() {
        view.navigateToLogin()
    }

    override fun loadConversations() {
        interactor.loadConversations(this)
    }

    override fun loadContacts() {
        interactor.loadContacts(this)
    }

    override fun executeLogout() {
        interactor.logout(this)
    }
}