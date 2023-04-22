package kr.kolorvxl.messagemanager.bukkit.broker

import kr.kolorvxl.messagemanager.core.broker.ExtensionSender
import kr.kolorvxl.messagemanager.core.broker.NationalExtensionSender
import net.md_5.bungee.api.chat.BaseComponent
import org.bukkit.command.CommandSender

open class BukkitSender(override val inner: CommandSender) : ExtensionSender<Array<BaseComponent>, CommandSender> {

    override fun sendResult(result: Array<BaseComponent>) {
        inner.spigot().sendMessage(*result)
    }

}


abstract class NationalBukkitSender<L : Enum<L>>(override val inner: CommandSender)
    : BukkitSender(inner), NationalExtensionSender<Array<BaseComponent>, L, CommandSender>