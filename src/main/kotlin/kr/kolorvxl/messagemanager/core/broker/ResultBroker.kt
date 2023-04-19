package kr.kolorvxl.messagemanager.core.broker

import kr.kolorvxl.messagemanager.core.LoadedMessage
import kr.kolorvxl.messagemanager.core.formatter.MessageFormatter
import kr.kolorvxl.messagemanager.core.storage.MessageStorage
import kr.kolorvxl.messagemanager.core.typeset.MessageType

interface ResultBroker<R, M : MessageFormatter<R, M>, L : Enum<L>> {

    val defaultLanguage: L

    val messageStorage: MessageStorage<L>

    fun messageFormatter(): MessageFormatter<R, M>


    fun ResultSender<R>.send(messageType: MessageType, function: M.() -> Unit) {
        send(
            if (this is NationalResultSender<*, *>) this.language as L
            else defaultLanguage,
            messageType,
            function
        )
    }

    fun ResultSender<R>.send(languageType: L, messageType: MessageType, function: M.() -> Unit) {
        val message = messageStorage[languageType][messageType]

        if (message !is LoadedMessage) {
            throw NullPointerException("")
        }

        if (message.value == null) {
            throw NullPointerException()
        }

        sendResult(
            messageFormatter().format(
                message.value,
                function
            )
        )
    }

}