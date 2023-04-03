package kr.kolorvxl.messagemanager.api

import kr.kolorvxl.messagemanager.core.MessageType
import kr.kolorvxl.messagemanager.core.MessageTypeSet

open class DynamicMessageTypeSet(function: DynamicMessageTypeSet.() -> Unit) : MessageTypeSet {

    override val messageTypes: List<MessageType> by lazy {
        TODO()
    }

    fun dir(name: String, function: DynamicMessageTypeSet.() -> Unit) {
        DynamicMessageTypeSet(function).messageTypes
        TODO()
    }

    fun type(name: String) {
        TODO()
    }

    fun hybrid(name: String, function: DynamicMessageTypeSet.() -> Unit) {
        DynamicMessageTypeSet(function).messageTypes
        TODO()
    }

}