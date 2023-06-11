package kr.kolorvxl.messagemanager.formatter

interface Formatter<R, F : Formatter<R, F>> {

    /**
     * Formats the given plain text using the given formatting commands.
     *
     * @param string The given plain text
     * @param commands The given formatting commands
     * @return The formatted output corresponding to the result type
     */
    fun format(string: String, commands: F.() -> Unit): R

    fun replace(obj: String, with: R)

    fun replace(obj: String, with: String, reprocessable: Boolean)

    fun result(plain: String, function: F.() -> Unit): R

    fun result(plain: String): R

    fun connect(list: List<R>): R

    fun reconstruct(): F

}