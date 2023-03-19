package kr.kolorvxl.messagemanager.core.message

data class FormatInformation<E : Enum<E>, M>(
    val storage: MessageStorage<E, M>,
    val language: Enum<E>
)

abstract class MessageFormatter<E : Enum<E>, M, I, O, R> {

    abstract fun format(
        messageStorage: MessageStorage<E, M>,
        language: Enum<E>,
        messageType: MessageType,
        function: MessageFormatter<E, M, I, O, R>.() -> Unit
    ): R

    abstract fun replace(from: I, to: O)

}