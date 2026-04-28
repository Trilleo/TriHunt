package net.trilleo.mc.plugins.trihunt.commands.game

import net.trilleo.mc.plugins.trihunt.data.ServerDataManager
import net.trilleo.mc.plugins.trihunt.managers.GameManager
import net.trilleo.mc.plugins.trihunt.registration.PluginCommand
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

class InitiateCommand(private val plugin: JavaPlugin) : PluginCommand(
    name = "initiate",
    description = "Reset game status",
    permission = "trihunt.initiate"
) {
    override fun execute(sender: CommandSender, args: Array<out String>): Boolean {
        GameManager(plugin).initiateGame()

        return true
    }
}