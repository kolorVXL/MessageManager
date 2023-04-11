package kr.kolorvxl.messagemanager.core


abstract class MessageStorage<E : Enum<E>> {
    abstract operator fun get(languageType: Enum<E>): SingleMessageStorage
}


abstract class FormalMessageStorage<E : Enum<E>> : MessageStorage<E>() {

    abstract val values: List<SingleMessageStorage>

    override operator fun get(languageType: Enum<E>): SingleMessageStorage = values[languageType.ordinal]

}

abstract class LazyInitMessageStorage<E : Enum<E>>(
    languageTypeEnum: Class<E>,
    messageTypeSet: MessageTypeSet,
    maxLoad: Int
) : FormalMessageStorage<E>() {

    override val values =
        List(languageTypeEnum.enumConstants.size) { languageIndex ->
            LazyInitSingleMessageStorage(
                messageTypeSet.messageTypes.size,
                { load(languageTypeEnum.enumConstants[languageIndex], it) },
                maxLoad
            )
        }

    abstract fun load(languageType: Enum<E>, messageType: MessageType): Message

}


open class SingleMessageStorage(val values: Array<Message>) {

    open operator fun get(messageType: MessageType): Message = values[messageType.identifier]

}

class LazyInitSingleMessageStorage(
    size: Int, val function: (MessageType) -> Message, private val maxLoad: Int = 3
) : SingleMessageStorage(Array(size) { NotInitMessage }) {

    override operator fun get(messageType: MessageType): Message {
        val index = messageType.identifier

        if (values[index] !is NotInitMessage) {
            return values[index]
        }

        for (i in 0 until maxLoad) {
            values[index] = function(messageType)
            if (!(values[index] is NotInitMessage || values[index] is NullMessage)) {
                break
            }
        }

        if (values[index] is NotInitMessage) {
            values[index] = NullMessage
        }

        return values[index]
    }

}