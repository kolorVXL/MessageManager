package kr.kolorvxl.messagemanager.core


data class MessageType(val name: List<String>, var identifier: Int)

interface MessageTypeSet {
    val messageTypes: List<MessageType>
}


sealed interface Message

object NotInitializedMessage : Message

object NullMessage : Message

data class NotNullMessage(val value: String) : Message