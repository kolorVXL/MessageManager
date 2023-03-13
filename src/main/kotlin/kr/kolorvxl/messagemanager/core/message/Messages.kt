package kr.kolorvxl.messagemanager.core.message

/**
 * The message set interface. You can include [MessageGroup] and [Message] in this.
 */
interface MessageSet

/**
 * The single message class.
 */
abstract class Message {
    var identifier: Int? = null
        private set
}

/**
 * Get sub [MessageObject] list of this class.
 */
val Any.subObjects: List<Any>
    get() = this::class
        .nestedClasses
        .mapNotNull { it.objectInstance }