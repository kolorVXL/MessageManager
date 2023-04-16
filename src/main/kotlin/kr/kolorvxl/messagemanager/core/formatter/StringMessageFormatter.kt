package kr.kolorvxl.messagemanager.core.formatter

import kr.kolorvxl.messagemanager.util.simpleReduce

class StringMessageFormatter : FormalMessageFormatter<String, StringMessageFormatter>() {

    override fun List<String>.concat() = this.simpleReduce()

    override fun String.toResultType() = this

    override fun selfConstruct() = StringMessageFormatter()

}