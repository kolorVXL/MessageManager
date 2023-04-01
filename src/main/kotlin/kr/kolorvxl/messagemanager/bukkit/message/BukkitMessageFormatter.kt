package kr.kolorvxl.messagemanager.bukkit.message

import kr.kolorvxl.messagemanager.core.message.MessageFormatter
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.ComponentBuilder

class BukkitMessageFormatter : MessageFormatter<Array<BaseComponent>, BukkitMessageFormatter>() {

    var color: ChatColor? = null
    var bold: Boolean? = null
    var italic: Boolean? = null
    var underline: Boolean? = null
    var strikethrough: Boolean? = null
    var obfuscate: Boolean? = null
    var font: String? = null

    private var innerValue: List<InnerMessage> = emptyList()

    override fun format(string: String, function: BukkitMessageFormatter.() -> Unit): Array<BaseComponent> {
        innerValue = listOf(InnerPlainMessage(string))
        function()

        val builder = ComponentBuilder()
        innerValue
            .map {
                when (it) {
                    is InnerPlainMessage -> it.value.toComponents()
                    is InnerComponentsMessage -> it.components
                }
            }
            .forEach(builder::append)
        return builder.create()
    }

    override fun String.to(r: Array<BaseComponent>) {
        TODO("Not yet implemented")
    }

    override fun List<Array<BaseComponent>>.concat(): Array<BaseComponent> {
        val builder = ComponentBuilder()
        this.forEach(builder::append)
        return builder.create()
    }

    private fun String.toComponents(): Array<BaseComponent> {
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

    sealed interface InnerMessage
    class InnerPlainMessage(val value: String) : InnerMessage
    class InnerComponentsMessage(val components: Array<BaseComponent>) : InnerMessage

}