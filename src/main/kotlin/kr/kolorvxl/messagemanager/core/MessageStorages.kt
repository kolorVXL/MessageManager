package kr.kolorvxl.messagemanager.core

abstract class MessageStorage<E : Enum<E>>(
    languages: Class<Enum<E>>, messageTypesWrapper: MessageTypesWrapper
) {
    abstract fun get(languageType: Enum<E>): SingleMessageStorage
}

data class SingleMessageStorage(val values: List<Message>) {
    operator fun get(messageType: MessageType) = messageType.identifier?.let { values[it] }
}