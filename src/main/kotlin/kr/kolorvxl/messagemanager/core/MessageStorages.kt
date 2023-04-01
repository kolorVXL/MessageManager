package kr.kolorvxl.messagemanager.core


abstract class MessageStorage<E : Enum<E>>(
    languages: Class<Enum<E>>, messageTypesWrapper: MessageTypesWrapper
) {
    abstract fun get(languageType: Enum<E>): SingleMessageStorage
}


abstract class FormalMessageStorage<E : Enum<E>>(
    languages: Class<Enum<E>>, messageTypesWrapper: MessageTypesWrapper
) : MessageStorage<E>(languages, messageTypesWrapper) {

    abstract val values: List<SingleMessageStorage>

    override fun get(languageType: Enum<E>): SingleMessageStorage = values[languageType.ordinal]

}


data class SingleMessageStorage(val values: Array<Message>) {

    operator fun get(messageType: MessageType) = messageType.identifier?.let { values[it] }

    override fun equals(other: Any?) = values.contentEquals((other as? SingleMessageStorage)?.values)

    override fun hashCode() = values.contentHashCode()

}