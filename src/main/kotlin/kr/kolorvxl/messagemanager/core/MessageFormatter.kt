package kr.kolorvxl.messagemanager.core

import kr.kolorvxl.messagemanager.bukkit.BukkitMessageFormatter
import kr.kolorvxl.messagemanager.util.intersperse
import net.md_5.bungee.api.chat.BaseComponent


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

    override fun String.to(r: R) {
        internalValue = internalValue.map { text ->
            when (text) {
                is InternalPlainText<R> -> text.value
                    .split(this)
                    .map { InternalPlainText<R>(it) }
                    .intersperse(InternalSpecialText(r))
                is InternalSpecialText<R> -> listOf(text)
            }
        }.flatten()
    }

    sealed interface InternalText<R>

    class InternalPlainText<R>(val value: String) : InternalText<R>

    class InternalSpecialText<R>(val components: R) : InternalText<R>

}

