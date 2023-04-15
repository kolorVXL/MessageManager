package kr.kolorvxl.messagemanager.bukkit

import kr.kolorvxl.messagemanager.core.*
import kr.kolorvxl.messagemanager.core.typeset.MessageTypeSet
import kr.kolorvxl.messagemanager.util.intersperse
import kr.kolorvxl.messagemanager.util.simpleReduce
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class BukkitMessageStorage<E : Enum<E>>(
    languageTypeEnum: Class<E>,
    messageTypeSet: MessageTypeSet,
    javaPlugin: JavaPlugin,
    private val transform: String.() -> String = { this }
) : FormalMessageStorage<E>() {

    override val values: List<SingleMessageStorage>

    init {
        val languageTypes = languageTypeEnum.enumConstants!!.sortedBy { it.ordinal }
        val messageTypes = messageTypeSet.messageTypes

        values = List(languageTypes.size) { i ->
            val languageType = languageTypes[i]
            val languageFileName = "${languageType.name.transform()}.yml"
            val languageFile = File(javaPlugin.dataFolder, languageFileName)

            if (!languageFile.exists()) {
                languageFile.parentFile.mkdirs()
                javaPlugin.saveResource(languageFileName, false)
            }

            var langConfig: YamlConfiguration? = YamlConfiguration()
            langConfig!!
                .runCatching { load(languageFile) }
                .onFailure { langConfig = null }

            SingleMessageStorage(
                Array(messageTypes.size) { j ->
                    val messageTypeName = messageTypes[j].name.intersperse(".").simpleReduce()
                    langConfig?.getString(messageTypeName)?.let { ValuableMessage(it) } ?: NullMessage
                }
            )
        }
    }
}