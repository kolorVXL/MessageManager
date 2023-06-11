package kr.kolorvxl.messagemanager.formatter

import kr.kolorvxl.messagemanager.util.simpleReduce

class StringFormatter : FormatterImpl<String, StringFormatter>() {

    override fun connect(list: List<String>) = list.simpleReduce()

    override fun result(plain: String) = plain

    override fun reconstruct() = StringFormatter()

}