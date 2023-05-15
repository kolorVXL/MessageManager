package kr.kolorvxl.messagemanager.bukkit.storage

import kr.kolorvxl.messagemanager.bukkit.formatter.BukkitFormatter
import kr.kolorvxl.messagemanager.message.Message
import kr.kolorvxl.messagemanager.storage.SimpleStorage
import net.md_5.bungee.api.chat.BaseComponent
import org.bukkit.command.CommandSender

abstract class BukkitSimpleStorage : SimpleStorage<Array<BaseComponent>, BukkitFormatter>() {

    override val formatter = BukkitFormatter()

}


internal fun SimpleStorage<Array<BaseComponent>, BukkitFormatter>.send(
    sender: CommandSender,
    plain: String,
    commands: BukkitFormatter.() -> Unit
) {
    sender.spigot().sendMessage(*format(plain.replace('&', '§'), commands))
}

internal fun SimpleStorage<Array<BaseComponent>, BukkitFormatter>.send(
    sender: CommandSender,
    message: Message,
    commands: BukkitFormatter.() -> Unit
) {
    send(sender, get(message), commands)
}