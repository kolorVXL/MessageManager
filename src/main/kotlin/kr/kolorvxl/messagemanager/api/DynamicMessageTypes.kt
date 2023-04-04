package kr.kolorvxl.messagemanager.api

import kr.kolorvxl.messagemanager.core.MessageType
import kr.kolorvxl.messagemanager.core.MessageTypeSet

open class DynamicMessageTypeSet(function: DynamicMessageTypeSet.() -> Unit) : MessageTypeSet {

    override val messageTypes: List<MessageType> by lazy {
        TODO()
    }

    fun undef(name: String) {
        TODO()
    }

    fun undef(name: String, function: DynamicMessageTypeSet.() -> Unit) {
        DynamicMessageTypeSet(function).messageTypes
        TODO()
    }

    fun def(name: String) {
        TODO()
    }

    fun def(name: String, function: DynamicMessageTypeSet.() -> Unit) {
        DynamicMessageTypeSet(function).messageTypes
        TODO()
    }

}