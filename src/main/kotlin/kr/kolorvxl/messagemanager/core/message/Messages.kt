package kr.kolorvxl.messagemanager.core.message

/**
 * The message set interface. You can include [MessageGroup] and [Message] in this.
 */
interface MessageSet

/**
 * The sealed interface containing [MessageGroup] and [Message].
 */
sealed interface MessageObject

/**
 * The message group interface. You can include [MessageGroup] and [Message] in this.
 */
interface MessageGroup : MessageObject

/**
 * The single message class.
 */
abstract class Message : MessageObject {
    var identifier: Int? = null
        private set
}

/**
 * Get sub [MessageObject]'s list of this class.
 */
val MessageGroup.subObjects: List<MessageObject>
    get() = this::class
        .nestedClasses
        .mapNotNull { it.objectInstance }
        .filterIsInstance<MessageObject>( )