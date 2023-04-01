package kr.kolorvxl.messagemanager.core


data class FormatInformation<E : Enum<E>>(
    val storage: MessageStorage<E>, val language: Enum<E>
)


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

    fun List<R>.concat(between: R): R =
        this
            .fold(emptyList<R>()) { list, r ->
                list
                    .plus(between)
                    .plus(r)
            }
            .concat()

}