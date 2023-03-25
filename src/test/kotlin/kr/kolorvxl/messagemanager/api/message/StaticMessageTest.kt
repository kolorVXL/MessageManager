package kr.kolorvxl.messagemanager.api.message

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


object TestingSet : StaticMessageSet() {

    object Test1 {

        object Test3 : End()

        object SubTest1 {

            object SubTest2 : End()

        }

    }

    object Test2 {

        object SubTest3 : End()

    }

    object Test3 : End()

}


class StaticMessageTest {

    @Test
    fun `static message test`() {
            TestingSet
                .messageTypes
                .map {
                    it.name.reduce { acc, s -> "$acc.$s" }
                }.forEach(::println)

        assertEquals(4, TestingSet.messageTypes.size)
    }

}