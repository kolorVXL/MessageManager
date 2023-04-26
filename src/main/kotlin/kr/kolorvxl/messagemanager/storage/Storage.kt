package kr.kolorvxl.messagemanager.storage

import kr.kolorvxl.messagemanager.language.Language
import kr.kolorvxl.messagemanager.language.LanguageSet
import kr.kolorvxl.messagemanager.message.Message
import kr.kolorvxl.messagemanager.message.MessageSet


operator fun List<List<String>>.get(language: Language) = get(language.id)

operator fun List<String>.get(message: Message) = get(message.id)


inline fun eachLanguage(languageSet: LanguageSet, init: (Language) -> List<String>): List<List<String>> {
    val languages = languageSet.languages
    return List(languages.size) {
        init(languages[it])
    }
}

inline fun eachMessage(messageSet: MessageSet, init: (Message) -> String): List<String> {
    val messages = messageSet.messages
    return List(messages.size) {
        init(messages[it])
    }
}