package kr.kolorvxl.messagemanager.core.typeset

data class MessageType(val name: List<String>, var identifier: Int)

interface MessageTypeSet {
    val messageTypes: List<MessageType>
}