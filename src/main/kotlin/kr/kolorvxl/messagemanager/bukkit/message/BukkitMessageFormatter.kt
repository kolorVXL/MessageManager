package kr.kolorvxl.messagemanager.bukkit.message

import kr.kolorvxl.messagemanager.core.message.MessageFormatter
import net.md_5.bungee.api.chat.BaseComponent

class BukkitMessageFormatter : MessageFormatter<List<BaseComponent>, BukkitMessageFormatter>() {

    override fun format(string: String, function: BukkitMessageFormatter.() -> Unit): List<BaseComponent> {
        TODO("Not yet implemented")
    }

    override fun String.to(r: List<BaseComponent>) {
        TODO("Not yet implemented")
    }

    override fun List<List<BaseComponent>>.concat(): List<BaseComponent> {
        TODO("Not yet implemented")
    }

}