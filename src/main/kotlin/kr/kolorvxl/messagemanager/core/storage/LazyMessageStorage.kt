package kr.kolorvxl.messagemanager.core.storage

import kr.kolorvxl.messagemanager.core.message.Message
import kr.kolorvxl.messagemanager.core.typeset.MessageType
import kr.kolorvxl.messagemanager.core.typeset.MessageTypeSet
import kr.kolorvxl.messagemanager.util.get
import kr.kolorvxl.messagemanager.util.size
import kotlin.reflect.KClass


open class LazyMessageStorage<E : Enum<E>>(
    languageTypeEnum: KClass<E>,
    messageTypeSet: MessageTypeSet,
    load: (Enum<E>, MessageType) -> Message,
) : FormalMessageStorage<E>() {

    override val values =
        List(languageTypeEnum.size) { index ->
            LazySingleMessageStorage(messageTypeSet) {
                load(languageTypeEnum[index], it)
            }
        }

}


class LazySingleMessageStorage(
    messageTypeSet: MessageTypeSet,
    private val loader: (MessageType) -> Message
) : SingleMessageStorage {

    override operator fun get(messageType: MessageType): Message {
        return when (val value = values[messageType.identifier]) {
            is LoadedLazyMessage -> value.message
            is NullLazyMessage -> loader(messageType).also { set(messageType, it) }
        }
    }

    override operator fun set(messageType: MessageType, message: Message) {
        values[messageType.identifier] = LoadedLazyMessage(message)
    }

    override fun iterator(): Iterator<Message> = values
        .indices
        .map { get(MessageType(emptyList(), it)) }
        .iterator()

    override val size = messageTypeSet.messageTypes.size

    private val values: Array<LazyMessage> = Array(size) { NullLazyMessage }

}


sealed interface LazyMessage

object NullLazyMessage : LazyMessage

data class LoadedLazyMessage(val message: Message) : LazyMessage