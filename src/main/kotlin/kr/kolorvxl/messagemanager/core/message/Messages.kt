package kr.kolorvxl.messagemanager.core.message


data class Message<M>(val value: M)

data class MessageType(val name: List<String>, var identifier: Int? = null)


abstract class MessageStorage<E : Enum<E>, M>(
    languages: List<Enum<E>>, messageTypes: List<MessageType>
) {

    abstract val values: List<List<Message<M>>>

    init {
        messageTypes.forEachIndexed { index, message ->
            message.identifier = index
        }
    }

    operator fun get(languageType: Enum<E>) = values[languageType.ordinal]

}