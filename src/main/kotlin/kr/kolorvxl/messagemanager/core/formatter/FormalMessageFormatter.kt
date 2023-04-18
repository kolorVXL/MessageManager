package kr.kolorvxl.messagemanager.core.formatter

import kr.kolorvxl.messagemanager.util.intersperse

abstract class FormalMessageFormatter<R, M : FormalMessageFormatter<R, M>> : MessageFormatter<R, M> {

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