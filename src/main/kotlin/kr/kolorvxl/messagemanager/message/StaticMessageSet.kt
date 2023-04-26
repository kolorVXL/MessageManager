package kr.kolorvxl.messagemanager.message

import kotlin.reflect.KClass

/*
 * C L A S S
 */

/**
 * The class of [StaticMessage] set. You can include [StaticMessage] in [messageClass].
 */
class StaticMessageSet(
    private val messageClass: KClass<*>,
    private val transform: String.() -> String = { this }
) : MessageSet {

    /**
     * The property for transforming [StaticMessage]s of [messageClass] to [List]<[Message]>.
     * @return Converted [List]<[Message]>.
     */
    override val messages: List<Message> by lazy {
        messageClass
            .sealedSubclasses
            .staticMessages
            .mapIndexed { index, value ->
                val message = MessageImpl(value.name.map(transform), index)
                value.message.message = message
                message
            }
    }

}

/**
 * The class of single static message. This exists to be used as elements of [StaticMessageSet].
 */
abstract class StaticMessage(var message: Message? = null) : Message {

    override val name: List<String>
        get() = message!!.name

    override val id: Int
        get() = message!!.id

}

/**
 * The class to use [StaticMessage] simply.
 */
abstract class End : StaticMessage()


private class NamedStaticMessage(val name: List<String>, val message: StaticMessage)

private val Collection<KClass<*>>.staticMessages: List<NamedStaticMessage>
    get() {
        val classes = this
            .filter { it.simpleName != null }
            .mapNotNull { it.objectInstance }

        val directSubs = classes
            .filterIsInstance<StaticMessage>()
            .map { NamedStaticMessage(it.className, it) }

        val indirectSubs = classes
            .map {
                it::class.nestedClasses.staticMessages.map { nsm ->
                    NamedStaticMessage(it.className + nsm.name, nsm.message)
                }
            }
            .flatten()

        return directSubs + indirectSubs
    }

private val Any.className: List<String> get() = listOf(this::class.simpleName!!)