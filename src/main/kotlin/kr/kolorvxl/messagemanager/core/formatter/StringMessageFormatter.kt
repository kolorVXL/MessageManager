package kr.kolorvxl.messagemanager.core.formatter

import kr.kolorvxl.messagemanager.util.simpleReduce

class StringMessageFormatter : MessageFormatterImpl<String, StringMessageFormatter>() {

    override fun connect(list: List<String>) = list.simpleReduce()

    override fun result(plain: String) = plain

    override fun copy() = StringMessageFormatter()

}