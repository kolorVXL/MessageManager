package kr.kolorvxl.messagemanager.core.message

data class MessageType(val name: List<String>, var identifier: Int? = null)


sealed interface Message

object NotInitializedMessage : Message

object NullMessage : Message

data class NotNullMessage(val value: String) : Message


interface MessageTypesWrapper {

    fun createMessageTypes(): List<MessageType>

    val messageTypes
        get() = createMessageTypes().mapIndexed { i, m -> m.copy(identifier = i) }

}