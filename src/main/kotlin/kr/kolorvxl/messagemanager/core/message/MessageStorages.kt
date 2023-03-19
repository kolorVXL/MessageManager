package kr.kolorvxl.messagemanager.core.message


abstract class MessageStorage<E : Enum<E>>(
    languages: List<Enum<E>>, messageTypes: List<MessageType>
) {

    abstract val values: List<SingleMessageStorage>

    init {
        messageTypes.forEachIndexed { index, message ->
            message.identifier = index
        }
    }

    open operator fun get(languageType: Enum<E>) = values[languageType.ordinal]

}


data class SingleMessageStorage(val values: List<Message>) {

    operator fun get(messageType: MessageType) =
        messageType.identifier?.let { values[it] }

}