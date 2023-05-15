package kr.kolorvxl.messagemanager.bukkit.formatter

import kr.kolorvxl.messagemanager.formatter.FormatterImpl
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.hover.content.Content
import net.md_5.bungee.api.chat.hover.content.Text
import java.awt.Color

class BukkitFormatter : FormatterImpl<Array<BaseComponent>, BukkitFormatter>() {

    var color: ChatColor? = null
    var bold: Boolean? = null
    var italic: Boolean? = null
    var underline: Boolean? = null
    var strikethrough: Boolean? = null
    var obfuscate: Boolean? = null
    var font: String? = null

    var clickEvent: ClickEvent? = null
    var hoverEvent: HoverEvent? = null

    override fun result(plain: String): Array<BaseComponent> {
        val builder = ComponentBuilder(plain)

        color?.let(builder::color)
        bold?.let(builder::bold)
        italic?.let(builder::italic)
        underline?.let(builder::underlined)
        strikethrough?.let(builder::strikethrough)
        obfuscate?.let(builder::obfuscated)
        font?.let(builder::font)

        clickEvent?.let(builder::event)
        hoverEvent?.let(builder::event)

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

    fun openUrl(url: String) = ClickEvent(ClickEvent.Action.OPEN_URL, url)
    fun openFile(file: String) = ClickEvent(ClickEvent.Action.OPEN_FILE, file)
    fun runCommand(command: String) = ClickEvent(ClickEvent.Action.RUN_COMMAND, command)
    fun suggestCommand(command: String) = ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command)
    fun changePage(command: String) = ClickEvent(ClickEvent.Action.CHANGE_PAGE, command)

    fun showText(vararg content: Content) = HoverEvent(HoverEvent.Action.SHOW_TEXT, *content)
    fun showText(component: Array<BaseComponent>) = showText(Text(component))
    fun showItem(vararg content: Content) = HoverEvent(HoverEvent.Action.SHOW_ITEM, *content)
    fun showEntity(vararg content: Content) = HoverEvent(HoverEvent.Action.SHOW_ENTITY, *content)

}