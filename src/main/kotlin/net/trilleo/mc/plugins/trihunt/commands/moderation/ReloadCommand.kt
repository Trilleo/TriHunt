package net.trilleo.mc.plugins.trihunt.commands.moderation

import net.trilleo.mc.plugins.trihunt.Main
import net.trilleo.mc.plugins.trihunt.registration.PluginCommand
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

/**
 * Reloads the plugin configuration from disk.
 *
 * Registered as `/trihunt reload` and requires the
 * `trihunt.reload` permission.
 */
class ReloadCommand(private val plugin: JavaPlugin) : PluginCommand(
    name = "reload",
    description = "Reload the plugin configuration",
    permission = "trihunt.reload"
) {
    override fun execute(sender: CommandSender, args: Array<out String>): Boolean {
        val main = plugin as? Main
        if (main == null) {
            sender.sendMessage("Error: Plugin instance type mismatch. Unable to reload configuration.")
            return true
        }
        main.pluginConfig.reload()
        sender.sendMessage("Configuration reloaded!")
        return true
    }
}