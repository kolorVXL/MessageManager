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
    undef("test-1") {
        def("test-3")
        undef("sub-test-1") {
            def("sub-test-2")
        }
        undef("test-2") {
            def("sub-test-3")
        }
    }
    def("test-3")
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