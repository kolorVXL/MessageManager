package kr.kolorvxl.messagemanager.bukkit

import kr.kolorvxl.messagemanager.core.FormalMessageFormatter
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.ComponentBuilder

class BukkitMessageFormatter : FormalMessageFormatter<Array<BaseComponent>, BukkitMessageFormatter>() {

    var color: ChatColor? = null
    var bold: Boolean? = null
    var italic: Boolean? = null
    var underline: Boolean? = null
    var strikethrough: Boolean? = null
    var obfuscate: Boolean? = null
    var font: String? = null

    override fun String.toResultType(): Array<BaseComponent> {
        val builder = ComponentBuilder(this)

        color?.let(builder::color)
        bold?.let(builder::bold)
        italic?.let(builder::italic)
        underline?.let(builder::underlined)
        strikethrough?.let(builder::strikethrough)
        obfuscate?.let(builder::obfuscated)
        font?.let(builder::font)

        return builder.create()
    }

    override fun List<Array<BaseComponent>>.concat(): Array<BaseComponent> {
        val builder = ComponentBuilder()
        this.forEach(builder::append)
        return builder.create()
    }

    override fun selfConstruct() = BukkitMessageFormatter()

}