package kr.kolorvxl.messagemanager.bukkit.formatter

import kr.kolorvxl.messagemanager.formatter.FormatterImpl
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.ComponentBuilder
import java.awt.Color

class BukkitFormatter : FormatterImpl<Array<BaseComponent>, BukkitFormatter>() {

    var color: ChatColor? = null
    var bold: Boolean? = null
    var italic: Boolean? = null
    var underline: Boolean? = null
    var strikethrough: Boolean? = null
    var obfuscate: Boolean? = null
    var font: String? = null

    override fun result(plain: String): Array<BaseComponent> {
        val builder = ComponentBuilder(plain)

        color?.let(builder::color)
        bold?.let(builder::bold)
        italic?.let(builder::italic)
        underline?.let(builder::underlined)
        strikethrough?.let(builder::strikethrough)
        obfuscate?.let(builder::obfuscated)
        font?.let(builder::font)

        return builder.create()
    }

    override fun connect(list: List<Array<BaseComponent>>): Array<BaseComponent> {
        val builder = ComponentBuilder()
        list.forEach(builder::append)
        return builder.create()
    }

    override fun copy() = BukkitFormatter()

    fun color(str: String): ChatColor = ChatColor.of(str)
    fun color(color: Color): ChatColor = ChatColor.of(color)

}