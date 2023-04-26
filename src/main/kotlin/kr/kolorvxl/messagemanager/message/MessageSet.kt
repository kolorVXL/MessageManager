package kr.kolorvxl.messagemanager.message

interface Message {
    val name: List<String>
    val id: Int
}

class MessageImpl(
    override val name: List<String>,
    override val id: Int
) : Message

interface MessageSet {
    val messages: List<Message>
}