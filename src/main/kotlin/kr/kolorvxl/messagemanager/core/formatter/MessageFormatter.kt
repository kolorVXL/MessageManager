package kr.kolorvxl.messagemanager.core.formatter


interface MessageFormatter<R, M : MessageFormatter<R, M>> : Cloneable {

    fun format(string: String, function: M.() -> Unit): R

    fun replace(obj: String, with: R)

    fun List<R>.concat(): R

    fun selfConstruct(): M


    fun message(string: String, function: M.() -> Unit): R = selfConstruct().format(string, function)

}