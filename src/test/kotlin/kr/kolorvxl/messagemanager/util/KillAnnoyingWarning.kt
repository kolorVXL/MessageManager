package kr.kolorvxl.messagemanager.util

import kr.kolorvxl.messagemanager.core.typeset.DynamicMessageTypeSet
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class KillAnnoyingWarning {
    @Test
    fun hi() {
        DynamicMessageTypeSet{}.plainMessageTypes
        assertEquals(1, 1)
    }
}