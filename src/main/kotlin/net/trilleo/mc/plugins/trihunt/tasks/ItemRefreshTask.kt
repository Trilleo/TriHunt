package net.trilleo.mc.plugins.trihunt.tasks

import net.trilleo.mc.plugins.trihunt.data.ServerDataManager
import net.trilleo.mc.plugins.trihunt.managers.CompassManager
import net.trilleo.mc.plugins.trihunt.registration.PluginTask
import net.trilleo.mc.plugins.trihunt.utils.TeamUtil
import org.bukkit.plugin.java.JavaPlugin

class ItemRefreshTask(private val plugin: JavaPlugin) : PluginTask(
    delay = 200L,
    period = 600L,
    async = true
) {
    override fun run() {
        val serverData = ServerDataManager.get()

        if (serverData.getString("gameStatus") != "inactive" && serverData.getBoolean("autoRefreshCompass")) {
            for (player in plugin.server.onlinePlayers) {
                if (TeamUtil.isInTeam(player, "hunter")) {
                    CompassManager(plugin).refreshCompass(player)
                }
            }
        }
    }
}