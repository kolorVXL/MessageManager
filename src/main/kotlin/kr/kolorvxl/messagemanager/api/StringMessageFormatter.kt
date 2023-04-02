package kr.kolorvxl.messagemanager.api

import kr.kolorvxl.messagemanager.core.FormalMessageFormatter
import kr.kolorvxl.messagemanager.util.simpleReduce

class StringMessageFormatter : FormalMessageFormatter<String, StringMessageFormatter>() {

    override fun List<String>.concat() = this.simpleReduce()

    override fun String.toResultType() = this

}