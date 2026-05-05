package net.trilleo.mc.plugins.trihunt.commands.info

import net.trilleo.mc.plugins.trihunt.registration.GUIManager
import net.trilleo.mc.plugins.trihunt.registration.PluginCommand
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class RecipeCommand : PluginCommand(
    name = "recipe",
    description = "View available plugin recipes"
) {
    override fun execute(sender: CommandSender, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("This command can only be run by players!")
            return true
        }

        GUIManager.open(sender, "recipe-book")
        return true
    }
}