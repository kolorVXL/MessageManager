package kr.kolorvxl.messagemanager.core.storage

import kr.kolorvxl.messagemanager.core.message.Message
import kr.kolorvxl.messagemanager.core.typeset.MessageType
import kr.kolorvxl.messagemanager.core.typeset.MessageTypeSet


abstract class FormalMessageStorage<E : Enum<E>> : MessageStorage<E> {

    abstract val values: List<SingleMessageStorage>

    override operator fun get(languageType: Enum<E>): SingleMessageStorage = values[languageType.ordinal]

}


class FormalSingleMessageStorage(messageTypeSet: MessageTypeSet, init: (MessageType) -> Message) : SingleMessageStorage {

    override operator fun get(messageType: MessageType): Message {
        return values[messageType.identifier]
    }

    override operator fun set(messageType: MessageType, message: Message) {
        values[messageType.identifier] = message
    }

    override val size get() = values.size

    override fun iterator() = values.iterator()


    private val values: Array<Message> = messageTypeSet.messageTypes.map(init).toTypedArray()

}