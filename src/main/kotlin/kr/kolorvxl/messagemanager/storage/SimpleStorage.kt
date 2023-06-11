package kr.kolorvxl.messagemanager.storage

import kr.kolorvxl.messagemanager.formatter.Formatter
import kr.kolorvxl.messagemanager.language.Language
import kr.kolorvxl.messagemanager.message.Message

abstract class SimpleStorage<R, F : Formatter<R, F>> {

    abstract val storage: List<List<String>>

    abstract val language: Language

    abstract val formatter: Formatter<R, F>


    fun format(plain: String, commands: F.() -> Unit) = formatter.reconstruct().format(plain, commands)

    fun get(message: Message): String = storage[language.id][message.id]

}