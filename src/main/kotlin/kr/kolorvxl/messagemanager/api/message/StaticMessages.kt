package kr.kolorvxl.messagemanager.api.message

import kr.kolorvxl.messagemanager.core.message.MessageType
import kr.kolorvxl.messagemanager.core.message.MessageTypesWrapper

/**
 * The message set interface. You can include [StaticMessage] in this.
 */
open class StaticMessageSet : MessageTypesWrapper {

    override fun toMessageTypes(): List<MessageType> =
        this::class
            .nestedClasses
            .staticMessages
            .map {
                val messageType = MessageType(it.first)
                it.second.messageType = messageType
                messageType
            }

}

val Any.staticMessages: List<Pair<List<String>, StaticMessage>>
    get() = this::class
        .nestedClasses
        .filter { it.objectInstance != null }
        .flatMap { klass ->
            val instance = klass.objectInstance!!
            val name = listOf(klass.simpleName ?: "")
            if (instance is StaticMessage) {
                listOf(Pair(name, instance))
            } else {
                instance.staticMessages.map { Pair(name + it.first, it.second) }
            }
        }

/**
 * The single message class.
 */
abstract class StaticMessage(var messageType: MessageType? = null)

abstract class End : StaticMessage()