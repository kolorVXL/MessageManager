package kr.kolorvxl.messagemanager.core.message


abstract class MessageStorage<E : Enum<E>>(
    languages: Class<Enum<E>>, messageTypes: List<MessageType>
) {

    init {
        messageTypes
            .forEachIndexed { index, message ->
                message.identifier = index
            }
    }

    constructor(languages: Class<Enum<E>>, messageTypesWrapper: MessageTypesWrapper) :
            this(languages, messageTypesWrapper.toMessageTypes())

    abstract fun get(languageType: Enum<E>): SingleMessageStorage

}


data class SingleMessageStorage(val values: List<Message>) {

    operator fun get(messageType: MessageType) =
        messageType.identifier?.let { values[it] }

}