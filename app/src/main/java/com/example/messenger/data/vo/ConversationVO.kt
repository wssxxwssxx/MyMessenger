package com.example.messenger.data.vo

data class ConversationVO(
    val conversationId: Long,
    val secondPartyUsername: String,
    val messages: ArrayList<MessageVO>
)