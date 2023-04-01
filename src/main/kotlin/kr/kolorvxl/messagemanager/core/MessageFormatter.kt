package kr.kolorvxl.messagemanager.core


abstract class MessageFormatter<R, M : MessageFormatter<R, M>> : Cloneable {

    abstract fun format(
        string: String, function: M.() -> Unit
    ): R

    abstract infix fun String.to(r: R)

    abstract fun List<R>.concat(): R


    @Suppress("UNCHECKED_CAST")
    fun message(
        string: String, function: M.() -> Unit
    ): R = ((clone() as M).format(string, function))

}