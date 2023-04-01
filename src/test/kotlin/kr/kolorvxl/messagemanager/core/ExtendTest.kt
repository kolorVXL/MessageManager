package kr.kolorvxl.messagemanager.core

import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class ExtendTest {

    @Test
    fun `extend test`() {
        val superSuper: SuperSuper = DuperDuper()
        assertTrue(superSuper.thisObject() is DuperDuper)
    }

}


open class SuperSuper {
    fun thisObject() = this
}

class DuperDuper : SuperSuper()