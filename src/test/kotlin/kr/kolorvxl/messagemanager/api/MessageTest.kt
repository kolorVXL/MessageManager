package kr.kolorvxl.messagemanager.api

import kr.kolorvxl.messagemanager.message.*
import kr.kolorvxl.messagemanager.util.kebabCase
import kr.kolorvxl.messagemanager.util.simpleReduce
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals


// StaticMessageSet

val testSet1 = StaticMessageSet(StaticSet::class, String::kebabCase)

sealed interface StaticSet


object Test1 : StaticSet {

    object Test3 : End()

    object SubTest1 {

        object SubTest2 : End()

    }

}

object Test2 : StaticSet {

    object SubTest3 : End()

    object Shadowed

}

object Test3 : StaticSet, End()



// DynamicTestSet

val testSet2 =  DynamicMessageSet {

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

}


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MessageTest {

    @ParameterizedTest
    @MethodSource("typeSets")
    fun `static message test`(set: MessageSet) {
        set.messages
            .map { it.name.simpleReduce(".") }
            .forEach(::println)

        assertEquals(4, set.messages.size)
    }

    private fun typeSets(): List<MessageSet> = listOf(testSet1, testSet2)

}