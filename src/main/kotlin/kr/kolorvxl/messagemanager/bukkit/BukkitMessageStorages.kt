package kr.kolorvxl.messagemanager.bukkit

import kr.kolorvxl.messagemanager.core.*
import kr.kolorvxl.messagemanager.core.storage.FormalMessageStorage
import kr.kolorvxl.messagemanager.core.storage.FormalSingleMessageStorage
import kr.kolorvxl.messagemanager.core.storage.SingleMessageStorage
import kr.kolorvxl.messagemanager.core.typeset.MessageTypeSet
import kr.kolorvxl.messagemanager.util.get
import kr.kolorvxl.messagemanager.util.intersperse
import kr.kolorvxl.messagemanager.util.simpleReduce
import kr.kolorvxl.messagemanager.util.size
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import kotlin.reflect.KClass

class BukkitMessageStorage<E : Enum<E>>(
    languageTypeEnum: KClass<E>,
    messageTypeSet: MessageTypeSet,
    javaPlugin: JavaPlugin,
    private val transform: String.() -> String = { this }
) : FormalMessageStorage<E>() {

    override val values: List<SingleMessageStorage>

    init {
        values = List(languageTypeEnum.size) { i ->
            val languageType = languageTypeEnum[i]
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

            FormalSingleMessageStorage(messageTypeSet) { messageType ->
                val messageTypeName = messageType.name.intersperse(".").simpleReduce()
                langConfig?.let {
                    LoadedMessage(it.getString(messageTypeName))
                } ?: UnloadableMessage
            }
        }
    }
}