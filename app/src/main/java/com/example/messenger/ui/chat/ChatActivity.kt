package com.example.messenger.ui.chat

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.messenger.R
import com.example.messenger.data.AppPreferences
import com.example.messenger.utils.message.Message
import com.stfalcon.chatkit.messages.MessageInput
import com.stfalcon.chatkit.messages.MessagesList
import com.stfalcon.chatkit.messages.MessagesListAdapter
import java.util.*

class ChatActivity : AppCompatActivity(), ChatView, MessageInput.InputListener {

    private var recipientId: Long = -1
    private lateinit var messageList: MessagesList
    private lateinit var messageInput: MessageInput
    private lateinit var preferences: AppPreferences
    private lateinit var presenter: ChatPresenter
    private lateinit var messageListAdapter: MessagesListAdapter<Message>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = intent.getStringExtra("RECIPIENT_NAME")

        preferences = AppPreferences.create(this)

        /*
         * Initialization of messageListAdapter.
         * First argument passed to constructor is the id of the current user
         * Second argument passed is an object of the type ImageLoader. null
         * is passed in this case because we do not need an ImageLoader in this case.
         */
        messageListAdapter = MessagesListAdapter(preferences.userDetails.id.toString(), null)
        presenter = ChatPresenterImpl(this)
        bindViews()

        /*
         * Parsing extras bundles within intent which launched the ChatActivity.
         * If either of the extras identified by the keys CONVERSATION_ID and
         * RECIPIENT_ID do not exist, -1 is returned as a default value.
         */
        val conversationId = intent.getLongExtra("CONVERSATION_ID", -1)
        recipientId = intent.getLongExtra("RECIPIENT_ID", -1)

        /*
         * If conversationId is not equal to -1, then the conversationId is valid,
         * hence load messages in the conversation.
         */
        if (conversationId != -1L) {
            presenter.loadMessages(conversationId)
        }
    }

    /**
     * Function override from MessageInput.InputListener
     * Called when a user submits a message with the MessageInput widget
     * @param input message input submitted by user
     */
    override fun onSubmit(input: CharSequence?): Boolean {
        // create a new Message object and add it to the start of the MessagesListAdapter
        messageListAdapter.addToStart(Message(
                preferences.userDetails.id, input.toString(), Date()), true)

        // start message sending procedure with the ChatPresenter
        presenter.sendMessage(recipientId, input.toString())

        return true
    }

    override fun showConversationLoadError() {
        Toast.makeText(this, "Unable to load thread. Please try again later.",
                Toast.LENGTH_LONG).show()
    }

    override fun showMessageSendError() {
        Toast.makeText(this, "Unable to send message. Please try again later.",
                Toast.LENGTH_LONG).show()
    }

    override fun getContext(): Context {
        return this
    }

    override fun getMessageListAdapter(): MessagesListAdapter<Message> {
        return messageListAdapter
    }

    override fun bindViews() {
        messageList = findViewById(R.id.messages_list)
        messageInput = findViewById(R.id.message_input)

        messageList.setAdapter(messageListAdapter)
        messageInput.setInputListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }



    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}