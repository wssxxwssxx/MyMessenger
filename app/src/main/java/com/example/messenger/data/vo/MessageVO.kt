package com.example.messenger.data.vo

data class MessageVO(
    val id: Long,
    val senderId: Long,
    val recipiendId: Long,
    val conversationId: Long,
    val body: String,
    val createAt: String
)