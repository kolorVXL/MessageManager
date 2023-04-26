package kr.kolorvxl.messagemanager.formatter

interface Formatter<R, F : Formatter<R, F>> {

    fun format(string: String, commands: F.() -> Unit): R

    fun replace(obj: String, with: R)

    fun result(plain: String, function: F.() -> Unit): R

    fun result(plain: String): R

    fun connect(list: List<R>): R

    fun copy(): F

}