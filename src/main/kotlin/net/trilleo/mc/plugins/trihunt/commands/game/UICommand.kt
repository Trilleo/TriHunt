package net.trilleo.mc.plugins.trihunt.commands.game

import net.trilleo.mc.plugins.trihunt.registration.GUIManager
import net.trilleo.mc.plugins.trihunt.registration.PluginCommand
import net.trilleo.mc.plugins.trihunt.utils.sendPrefixed
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class UICommand : PluginCommand(
    name = "ui",
    description = "Open the main UI",
    usage = "/trihunt ui",
) {
    override fun execute(sender: CommandSender, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("This command can only be used by players.")
            return true
        }
        GUIManager.open(sender, "main")
        sender.sendPrefixed("<green>Opened TriHunt main UI.")
        return true
    }
}