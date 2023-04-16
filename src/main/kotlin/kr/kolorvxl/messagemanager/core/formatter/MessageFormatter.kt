package kr.kolorvxl.messagemanager.core.formatter


abstract class MessageFormatter<R, M : MessageFormatter<R, M>> : Cloneable {

    abstract fun format(string: String, function: M.() -> Unit): R

    abstract fun replace(obj: String, with: R)

    abstract fun List<R>.concat(): R

    abstract fun selfConstruct(): M


    fun message(string: String, function: M.() -> Unit): R = selfConstruct().format(string, function)

}