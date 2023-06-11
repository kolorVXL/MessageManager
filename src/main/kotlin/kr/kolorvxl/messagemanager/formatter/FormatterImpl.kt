package kr.kolorvxl.messagemanager.formatter

import kr.kolorvxl.messagemanager.util.intersperse

abstract class FormatterImpl<R, F : FormatterImpl<R, F>> : Formatter<R, F> {

    @Suppress("UNCHECKED_CAST")
    override fun format(string: String, commands: F.() -> Unit): R {
        rawValues = listOf(PlainValue(string, reprocessable = true))
        (this as F).commands()

        val outputArray = ArrayList<R>()
        var buffer = ""
        val flush = flush@{
            if (buffer == "") {
                return@flush
            }
            outputArray.add(result(buffer))
            buffer = ""
        }

        rawValues.forEach {
            if (it is ResultValue) {
                flush()
                outputArray.add(it.resultValue)
            }

            if (it is PlainValue) {
                buffer += it.plainValue
            }
        }
        flush()

        return connect(outputArray)
    }

    override fun replace(obj: String, with: R) {
        rawValues = rawValues
            .map { it.replaceOnlyReprocessablePlain(obj, ResultValue(with)) }
            .flatten()
    }

    override fun replace(obj: String, with: String, reprocessable: Boolean) {
        rawValues = rawValues
            .map { it.replaceOnlyReprocessablePlain(obj, PlainValue(with, reprocessable)) }
            .flatten()
    }

    override fun result(plain: String, function: F.() -> Unit): R = reconstruct().format(plain, function)


    open var rawValues: List<RawValue<R>> = emptyList()

    private fun RawValue<R>.replaceOnlyReprocessablePlain(obj: String, with: RawValue<R>): List<RawValue<R>> {
        if (this !is PlainValue) {
            return listOf(this)
        }

        if (!this.reprocessable) {
            return listOf(this)
        }

        return this.plainValue.replaceWithRawValue(obj, with)
    }

    private fun String.replaceWithRawValue(obj: String, with: RawValue<R>): List<RawValue<R>> {
        return this
            .split(obj)
            .map { PlainValue<R>(it, reprocessable = true) }
            .intersperse(with)
    }

}

sealed interface RawValue<R>

class PlainValue<R>(val plainValue: String, val reprocessable: Boolean) : RawValue<R>

class ResultValue<R>(val resultValue: R) : RawValue<R>