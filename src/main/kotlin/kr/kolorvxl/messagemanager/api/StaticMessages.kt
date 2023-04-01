package kr.kolorvxl.messagemanager.api

import kr.kolorvxl.messagemanager.core.MessageType
import kr.kolorvxl.messagemanager.core.MessageTypesWrapper
import kotlin.reflect.KClass

/**
 * The message set interface. You can include [StaticMessage] in this.
 */
open class StaticMessageSet(private val transform: String.() -> String = { this }) : MessageTypesWrapper {

    override val messageTypes: List<MessageType> =
        this.staticMessages
            .mapIndexed { index, value ->
                val messageType = MessageType(
                    value.name.map(transform),
                    index
                )
                value.message.messageType = messageType
                messageType
            }

}

abstract class StaticMessage(var messageType: MessageType? = null)

abstract class End : StaticMessage()


data class StaticMessageWrapper(val name: List<String>, val message: StaticMessage) {
    constructor(name: String, message: StaticMessage) : this(listOf(name), message)
}

val Any.staticMessages: List<StaticMessageWrapper>
    get() {
        val classes = this::class
            .nestedClasses
            .filter { it.simpleName != null }
            .mapNotNull { it.objectInstance }

        val directSubs = classes
            .filterIsInstance<StaticMessage>()
            .map { StaticMessageWrapper(it::class.simpleName!!, it) }

        val indirectSubs = classes
            .flatMap { ins ->
                ins.staticMessages
                    .map { it.copy(name = listOf(ins::class.simpleName!!) + it.name) }
            }

        return directSubs + indirectSubs
    }