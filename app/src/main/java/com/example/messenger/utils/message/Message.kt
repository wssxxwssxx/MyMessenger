package com.example.messenger.utils.message

import com.stfalcon.chatkit.commons.models.IMessage
import com.stfalcon.chatkit.commons.models.IUser
import java.util.*

/**
 * @author Iyanu Adelekan. 29/10/2017.
 */
data class Message(private val authorId: Long, private val body: String,
                   private val createdAt: Date) : IMessage {

    override fun getId(): String {
        return authorId.toString()
    }

    override fun getCreatedAt(): Date {
        return createdAt
    }

    override fun getUser(): IUser {
        return Author(authorId, "")
    }

    override fun getText(): String {
        return body
    }

}