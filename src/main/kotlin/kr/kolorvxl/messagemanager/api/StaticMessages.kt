package kr.kolorvxl.messagemanager.api

import kr.kolorvxl.messagemanager.core.MessageType
import kr.kolorvxl.messagemanager.core.MessageTypesWrapper

/**
 * The class of [StaticMessage] set. You can include [StaticMessage] in this.
 */
abstract class StaticMessageSet(private val transform: String.() -> String = { this }) : MessageTypesWrapper {

    /**
     * The function to convert internal [List]<[StaticMessage]>s to [List]<[MessageType]>.
     * @return Converted [List]<[MessageType]>.
     */
    override val messageTypes: List<MessageType> = this.staticMessages.mapIndexed { index, value ->
        val messageType = MessageType(
            value.name.map(transform), index
        )
        value.message.messageType = messageType
        messageType
    }

}

/**
 * The class of single static message. This exists to be used as elements of [StaticMessageSet].
 */
abstract class StaticMessage(var messageType: MessageType? = null)

/**
 * The class to use [StaticMessage] simply.
 */
abstract class End : StaticMessage()


/**
 * The class that contains name and [StaticMessage]. This exists to be used as the return type of [Any.staticMessages].
 */
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

        val indirectSubs = classes
            .map {
                it.staticMessages.map { nsm ->
                    NamedStaticMessage(it.className + nsm.name, nsm.message)
                }
            }
            .flatten()

        return directSubs + indirectSubs
    }

/**
 * The property to use the name of the receiver object simply.
 */
private val Any.className: List<String> get() = listOf(this::class.simpleName!!)