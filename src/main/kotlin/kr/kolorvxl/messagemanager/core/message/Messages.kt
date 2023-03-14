package kr.kolorvxl.messagemanager.core.message

sealed class Messages(open val name: String)

data class MessageGroup(override val name: String, val subMessages: List<Messages>) : Messages(name)
data class Message(override val name: String) : Messages(name)