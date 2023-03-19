package kr.kolorvxl.messagemanager.core.message


data class MessageType(val name: List<String>, var identifier: Int? = null)


sealed interface Message<M>

class NotInitializedMessage<M> : Message<M>

class NullMessage<M> : Message<M>

data class NotNullMessage<M>(val value: M) : Message<M>