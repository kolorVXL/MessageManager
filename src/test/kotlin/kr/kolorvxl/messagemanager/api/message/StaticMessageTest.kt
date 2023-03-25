package kr.kolorvxl.messagemanager.api.message

import org.junit.jupiter.api.Test


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
                .staticMessages
                .map {
                    it.first.reduce { a, b ->
                        "$a.$b"
                    }
                }.forEach(::println)
    }

}