package kr.kolorvxl.messagemanager.api

import kr.kolorvxl.messagemanager.util.kebabCase
import kr.kolorvxl.messagemanager.util.simpleReduce
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


object TestingTypeSet : StaticMessageTypeSet(String::kebabCase) {

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


class StaticMessageTypeTest {

    @Test
    fun `static message test`() {
            TestingTypeSet
                .messageTypes
                .map { it.name.simpleReduce(".") }
                .forEach(::println)

        assertEquals(4, TestingTypeSet.messageTypes.size)
    }

}