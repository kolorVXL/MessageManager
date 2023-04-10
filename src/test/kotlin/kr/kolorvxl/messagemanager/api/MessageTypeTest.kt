package kr.kolorvxl.messagemanager.api

import kr.kolorvxl.messagemanager.core.MessageTypeSet
import kr.kolorvxl.messagemanager.util.kebabCase
import kr.kolorvxl.messagemanager.util.simpleReduce
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
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

        object Shadowed

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

            undef("shadowed")

        }

    }

    def("test-3")

})


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MessageTypeTest {

    @ParameterizedTest
    @MethodSource("typeSets")
    fun `static message test`(set: MessageTypeSet) {
        set.messageTypes
            .map { it.name.simpleReduce(".") }
            .forEach(::println)

        assertEquals(4, StaticTestTypeSet.messageTypes.size)
    }

    private fun typeSets(): List<MessageTypeSet> = listOf(StaticTestTypeSet, DynamicTestTypeSet)

}