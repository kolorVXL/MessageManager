package kr.kolorvxl.messagemanager.core.util

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class StringUtilTest {

    @Test
    fun splitCamelTest() {

        val list = listOf(
            "My123Camel" to "My 123 Camel",
            "SSKill423er" to "SS Kill 423 er",
            "myCamelSeasonTwo" to "my Camel Season Two"
        )

        list.forEach {
            assertEquals(
                it.second,
                it.first.splitCamel().reduce { acc, s -> "$acc $s" }
            )
        }

    }

}