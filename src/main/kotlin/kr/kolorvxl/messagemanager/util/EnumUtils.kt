package kr.kolorvxl.messagemanager.util

import kotlin.reflect.KClass


fun <E : Enum<E>> Class<E>.toList(): List<E> {
    return this.enumConstants!!.sortedBy { it.ordinal }
}

fun <E : Enum<E>> KClass<E>.toList(): List<E> {
    return this.java.toList()
}


operator fun <E : Enum<E>> Class<E>.get(index: Int): E {
    return toList()[index]
}

operator fun <E : Enum<E>> KClass<E>.get(index: Int): E {
    return this.java[index]
}


val <E : Enum<E>> Class<E>.size: Int
    get() = toList().size

val <E : Enum<E>> KClass<E>.size: Int
    get() = this.java.size


fun <E : Enum<E>> Class<E>.iterator(): Iterator<E> {
    return toList().iterator()
}

fun <E : Enum<E>> KClass<E>.iterator(): Iterator<E> {
    return this.java.iterator()
}