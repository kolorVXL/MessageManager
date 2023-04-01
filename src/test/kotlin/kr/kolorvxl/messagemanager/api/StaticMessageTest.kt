package kr.kolorvxl.messagemanager.api

import kr.kolorvxl.messagemanager.util.kebabCase
import kr.kolorvxl.messagemanager.util.simpleReduce
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


object TestingSet : StaticMessageSet(String::kebabCase) {

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
                .map { it.name.simpleReduce(".") }
                .forEach(::println)

        assertEquals(4, TestingSet.messageTypes.size)
    }

}