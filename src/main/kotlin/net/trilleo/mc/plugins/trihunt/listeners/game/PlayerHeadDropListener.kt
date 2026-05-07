package net.trilleo.mc.plugins.trihunt.listeners.game

import net.trilleo.mc.plugins.trihunt.data.ServerDataManager
import net.trilleo.mc.plugins.trihunt.utils.TeamUtil
import net.trilleo.mc.plugins.trihunt.utils.itemStack
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.plugin.java.JavaPlugin

class PlayerHeadDropListener(plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        val player = event.player
        val killer = event.damageSource.causingEntity
        val serverData = ServerDataManager.get()

        if (!serverData.getBoolean("customItems", true)) return

        if (killer is Player) {
            if (TeamUtil.isInTeam(killer, "speedrunner") && TeamUtil.isInTeam(player, "hunter")) {
                val head = itemStack(Material.PLAYER_HEAD) {
                    name("<dark_red>${player.name}'s Head")
                    meta {
                        (this as SkullMeta)
                            .owningPlayer = player
                    }
                }

                event.drops.add(head)
            }
        }
    }
}