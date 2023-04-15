package kr.kolorvxl.messagemanager.core

import kr.kolorvxl.messagemanager.util.intersperse


abstract class MessageFormatter<R, M : MessageFormatter<R, M>> : Cloneable {

    abstract fun format(
        string: String, function: M.() -> Unit
    ): R

    abstract fun replace(obj: String, with: R)

    abstract fun List<R>.concat(): R

    abstract fun selfConstruct(): M


    fun message(string: String, function: M.() -> Unit): R = selfConstruct().format(string, function)

}

abstract class FormalMessageFormatter<R, M : FormalMessageFormatter<R, M>> : MessageFormatter<R, M>() {

    abstract fun String.toResultType(): R


    private var internalValue: List<InternalText<R>> = emptyList()

    @Suppress("UNCHECKED_CAST")
    override fun format(string: String, function: M.() -> Unit): R {
        internalValue = listOf(InternalPlainText(string))
        (this as M).function()

        return internalValue
            .map {
                when(it) {
                    is InternalPlainText -> it.value.toResultType()
                    is InternalSpecialText -> it.components
                }
            }
            .concat()
    }

    override fun replace(obj: String, with: R) {
        internalValue = internalValue.map { text ->
            when (text) {
                is InternalPlainText<R> -> text.value
                    .split(obj)
                    .map { InternalPlainText<R>(it) }
                    .intersperse(InternalSpecialText(with))
                is InternalSpecialText<R> -> listOf(text)
            }
        }.flatten()
    }

    sealed interface InternalText<R>

    class InternalPlainText<R>(val value: String) : InternalText<R>

    class InternalSpecialText<R>(val components: R) : InternalText<R>

}

