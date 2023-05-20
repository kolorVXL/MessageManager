package kr.kolorvxl.messagemanager.bukkit.storage

import kr.kolorvxl.messagemanager.language.LanguageSet
import kr.kolorvxl.messagemanager.message.MessageSet
import kr.kolorvxl.messagemanager.storage.eachLanguage
import kr.kolorvxl.messagemanager.storage.eachMessage
import kr.kolorvxl.messagemanager.util.intersperse
import kr.kolorvxl.messagemanager.util.simpleReduce
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

inline fun bukkitStorage(
    languageSet: LanguageSet,
    messageSet: MessageSet,
    javaPlugin: JavaPlugin,
    transform: String.() -> String = { this }
): List<List<String>> {
    return eachLanguage(languageSet) { language ->
        val file = File(javaPlugin.dataFolder, "${language.name.transform()}.yml")

        if (!file.exists()) {
            file.parentFile.mkdirs()
            runCatching { javaPlugin.saveResource(file.name, false) }
        }

        var langConfig: YamlConfiguration? = YamlConfiguration()
        langConfig!!
            .runCatching { load(file) }
            .onFailure { langConfig = null }

        eachMessage(messageSet) { message ->
            val messageName = message.name.intersperse(".").simpleReduce()
            langConfig?.let { it.getString(messageName) }?.replace('&', '§') ?: "Unloadable"
        }
    }
}