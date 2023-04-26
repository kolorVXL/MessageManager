package kr.kolorvxl.messagemanager.formatter

import kr.kolorvxl.messagemanager.util.intersperse

abstract class FormatterImpl<R, M : FormatterImpl<R, M>> : Formatter<R, M> {

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


    protected open var rawValues: List<RawValue> = emptyList()

    protected inner class RawValue(val plainValue: String, val resultValue: R? = null) {
        fun result() = resultValue ?: result(plainValue)
    }


    private fun String.replaceWithResult(obj: String, with: R): List<RawValue> {
        return this
            .split(obj)
            .map { RawValue(it) }
            .intersperse(RawValue("", with))
    }

}
