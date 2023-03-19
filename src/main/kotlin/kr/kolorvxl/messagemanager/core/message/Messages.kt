package kr.kolorvxl.messagemanager.core.message

data class MessageType(val name: List<String>, var identifier: Int? = null)


sealed interface Message

object NotInitializedMessage : Message

object NullMessage : Message

data class NotNullMessage(val value: String) : Message


interface MessageTypesWrapper {
    fun toMessageTypes(): List<MessageType>
}