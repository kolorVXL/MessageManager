package kr.kolorvxl.messagemanager.api

import kr.kolorvxl.messagemanager.core.MessageType
import kr.kolorvxl.messagemanager.core.MessageTypesWrapper

/**
 * The message set interface. You can include [StaticMessage] in this.
 */
open class StaticMessageSet(private val transform: String.() -> String = { this }) : MessageTypesWrapper {

    override val messageTypes: List<MessageType> = this.staticMessages.mapIndexed { index, value ->
        val messageType = MessageType(
            value.name.map(transform), index
        )
        value.message.messageType = messageType
        messageType
    }

}

abstract class StaticMessage(var messageType: MessageType? = null)

abstract class End : StaticMessage()


data class NamedStaticMessage(val name: List<String>, val message: StaticMessage)

val Any.staticMessages: List<NamedStaticMessage>
    get() {
        val classes = this::class
            .nestedClasses
            .filter { it.simpleName != null }
            .mapNotNull { it.objectInstance }

        val directSubs = classes
            .filterIsInstance<StaticMessage>()
            .map { NamedStaticMessage(it.className, it) }

        val indirectSubs = classes.flatMap {
            it.staticMessages.map { nsm ->
                NamedStaticMessage(it.className + nsm.name, nsm.message)
            }
        }

        return directSubs + indirectSubs
    }

private val Any.className
    get() = listOf(this::class.simpleName!!)