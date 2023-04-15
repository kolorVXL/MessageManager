package kr.kolorvxl.messagemanager.core.typeset

/**
 * The class of [StaticMessageType] set. You can include [StaticMessageType] in this.
 */
abstract class StaticMessageTypeSet(private val transform: String.() -> String = { this }) : MessageTypeSet {

    /**
     * The function to convert internal [List]<[StaticMessageType]>s to [List]<[MessageType]>.
     * @return Converted [List]<[MessageType]>.
     */
    final override val messageTypes: List<MessageType> by lazy {
        this.staticMessages.mapIndexed { index, value ->
            val messageType = MessageType(value.name.map(transform), index)
            value.message.messageType = messageType
            messageType
        }
    }

}

/**
 * The class of single static message. This exists to be used as elements of [StaticMessageTypeSet].
 */
abstract class StaticMessageType(var messageType: MessageType? = null)

/**
 * The class to use [StaticMessageType] simply.
 */
abstract class End : StaticMessageType()


/**
 * The class that contains name and [StaticMessageType]. This exists to be used as the return type of [Any.staticMessages].
 */
data class NamedStaticMessage(val name: List<String>, val message: StaticMessageType)

val Any.staticMessages: List<NamedStaticMessage>
    get() {
        val classes = this::class
            .nestedClasses
            .filter { it.simpleName != null }
            .mapNotNull { it.objectInstance }

        val directSubs = classes
            .filterIsInstance<StaticMessageType>()
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