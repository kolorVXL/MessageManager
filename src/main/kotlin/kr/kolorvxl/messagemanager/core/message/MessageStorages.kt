package kr.kolorvxl.messagemanager.core.message


abstract class MessageStorage<E : Enum<E>, M>(
    languages: List<Enum<E>>, messageTypes: List<MessageType>
) {

    abstract val values: List<SingleMessageStorage<M>>

    init {
        messageTypes.forEachIndexed { index, message ->
            message.identifier = index
        }
    }

    open operator fun get(languageType: Enum<E>) = values[languageType.ordinal]

}


data class SingleMessageStorage<M>(val values: List<Message<M>>) {

    operator fun get(messageType: MessageType) =
        messageType.identifier?.let { values[it] }

}