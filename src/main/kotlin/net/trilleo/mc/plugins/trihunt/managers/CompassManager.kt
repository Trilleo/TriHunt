package net.trilleo.mc.plugins.trihunt.managers

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.trilleo.mc.plugins.trihunt.utils.TeamUtil
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class CompassManager(private val plugin: JavaPlugin) {
    fun refreshCompass(player: Player) {
        var speedrunnerUUIDs = TeamUtil.getTeam("speedrunner")?.members
        if (speedrunnerUUIDs != null) {
            for (uuid in speedrunnerUUIDs) {
                val target = Bukkit.getPlayer(uuid)

                if (target != null && target.isOnline) {

                    if (player.world == target.world) {
                        player.compassTarget = target.location
                        player.sendActionBar(
                            Component.text("Tracked player ").color(NamedTextColor.GREEN)
                                .append(Component.text(target.name).color(NamedTextColor.YELLOW))
                        )
                    } else {
                        player.sendActionBar(
                            Component.text("Player ").color(NamedTextColor.RED)
                                .append(Component.text(target.name).color(NamedTextColor.YELLOW))
                                .append(Component.text(" is in a different world!").color(NamedTextColor.RED))
                        )
                    }

                    break
                }
            }
        }
    }
}