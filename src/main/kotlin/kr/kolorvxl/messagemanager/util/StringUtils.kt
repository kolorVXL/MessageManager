package kr.kolorvxl.messagemanager.util

private const val OUT = 0
private const val UPPERCASE = 1
private const val LOWERCASE = 2
private const val DIGIT = 3

private val splitCamelFlag: List<Boolean> = List(64) {
    val prev = (it and 0b110000) shr 4
    val curr = (it and 0b001100) shr 2
    val next = (it and 0b000011) shr 0

    return@List if (prev == curr) (prev == UPPERCASE) && (next == LOWERCASE)
    else !((prev == UPPERCASE) && (curr == LOWERCASE))
}

/**
 * Splits this camel case string to a list of strings
 */
fun String.splitCamel(): List<String> {

    val words = ArrayList<String>()
    var prevIndex = 0

    val types = this.map {
        when {
            it.isUpperCase() -> UPPERCASE
            it.isLowerCase() -> LOWERCASE
            it.isDigit() -> DIGIT
            else -> OUT
        }
    }

    indices.forEach { currIndex ->
        val prev = types.getOrNull(currIndex - 1) ?: OUT
        val curr = types.getOrNull(currIndex + 0) ?: OUT
        val next = types.getOrNull(currIndex + 1) ?: OUT

        if (splitCamelFlag[(prev shl 4) or (curr shl 2) or (next shl 0)]) {
            words.add(substring(prevIndex, currIndex))
            prevIndex = currIndex
        }
    }

    words.add(substring(prevIndex, length))
    return words.drop(1)

}

fun List<String>.simpleReduce() = simpleReduce("")

fun List<String>.simpleReduce(between: String) =
    this.reduce { acc, s -> "$acc$between$s" }

fun String.snakeCase() =
    this
        .splitCamel()
        .map(String::lowercase)
        .simpleReduce("_")

fun String.kebabCase() =
    this
        .splitCamel()
        .map(String::lowercase)
        .simpleReduce("-")