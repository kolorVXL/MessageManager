package kr.kolorvxl.messagemanager.core.formatter

import kr.kolorvxl.messagemanager.util.intersperse

abstract class MessageFormatterImpl<R, M : MessageFormatterImpl<R, M>> : MessageFormatter<R, M> {

    protected open var rawValues: List<RawValue> = emptyList()

    @Suppress("UNCHECKED_CAST")
    override fun format(string: String, commands: M.() -> Unit): R {
        rawValues = listOf(RawValue(string))
        (this as M).commands()
        return connect(rawValues.map { it.result() })
    }

    override fun replace(obj: String, with: R) {
        rawValues = rawValues
            .map { it.resultValue?.run { listOf(it) } ?: it.plainValue.replaceWithResult(obj, with) }
            .flatten()
    }

    override fun result(plain: String, function: M.() -> Unit): R = copy().format(plain, function)


    private fun String.replaceWithResult(obj: String, with: R): List<RawValue> {
        return this
            .split(obj)
            .map { RawValue(it) }
            .intersperse(RawValue("", with))
    }

    protected inner class RawValue(val plainValue: String, val resultValue: R? = null) {
        fun result() = resultValue ?: result(plainValue)
    }

}