package kr.kolorvxl.messagemanager.util

fun <T> List<T>.intersperse(between: T) =
    this
        .map { listOf(it, between) }
        .flatten()
        .dropLast(1)