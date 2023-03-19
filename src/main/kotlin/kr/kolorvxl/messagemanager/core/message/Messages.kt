package kr.kolorvxl.messagemanager.core.message


data class Message(val value: String)

data class MessageType(val name: List<String>, var identifier: Int? = null)


abstract class MessageStorage<E : Enum<E>>(
    languages: List<Enum<E>>, messageTypes: List<MessageType>
) {

    abstract val values: List<List<Message>>

    init {
        messageTypes.forEachIndexed { index, message ->
            message.identifier = index
        }
    }

    operator fun get(languageType: Enum<E>) = values[languageType.ordinal]

}