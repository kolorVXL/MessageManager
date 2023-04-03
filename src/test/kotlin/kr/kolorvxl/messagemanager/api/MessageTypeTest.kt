package kr.kolorvxl.messagemanager.api

import kr.kolorvxl.messagemanager.util.kebabCase
import kr.kolorvxl.messagemanager.util.simpleReduce
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


object StaticTestTypeSet : StaticMessageTypeSet(String::kebabCase) {

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

object DynamicTestTypeSet : DynamicMessageTypeSet({
    dir("test-1") {
        type("test-3")
        dir("sub-test-1") {
            type("sub-test-2")
        }
        dir("test-2") {
            type("sub-test-3")
        }
    }
    type("test-3")
})


class StaticMessageTypeTest {

    @Test
    fun `static message test`() {
        StaticTestTypeSet
            .messageTypes
            .map { it.name.simpleReduce(".") }
            .forEach(::println)

        assertEquals(4, StaticTestTypeSet.messageTypes.size)
    }

}