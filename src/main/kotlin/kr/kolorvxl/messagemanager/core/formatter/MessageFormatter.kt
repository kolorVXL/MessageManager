package kr.kolorvxl.messagemanager.core.formatter

interface MessageFormatter<R, M : MessageFormatter<R, M>> {

    fun format(string: String, commands: M.() -> Unit): R

    fun replace(obj: String, with: R)

    fun result(plain: String, function: M.() -> Unit): R

    fun result(plain: String): R

    fun connect(list: List<R>): R

    fun copy(): M

}