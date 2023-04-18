package kr.kolorvxl.messagemanager.core.storage

import kr.kolorvxl.messagemanager.core.Message
import kr.kolorvxl.messagemanager.core.typeset.MessageType


interface MessageStorage<E : Enum<E>> {

    operator fun get(languageType: Enum<E>): SingleMessageStorage

}


interface SingleMessageStorage : Iterable<Message>  {

    operator fun get(messageType: MessageType): Message

    operator fun set(messageType: MessageType, message: Message)

    val size: Int

}


