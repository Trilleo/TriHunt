package net.trilleo.mc.plugins.trihunt.listeners.item

import net.trilleo.mc.plugins.trihunt.utils.PDCEntryUtil
import net.trilleo.mc.plugins.trihunt.utils.PDCUtil
import org.bukkit.GameMode
import org.bukkit.entity.Fireball
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin
import java.util.UUID

class ShootableFireballListener(private val plugin: JavaPlugin) : Listener {

    private val cooldownMillis = 1000L
    private val lastShotAt = mutableMapOf<UUID, Long>()

    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        val player = event.player
        val item = event.item

        if (event.action != Action.RIGHT_CLICK_BLOCK && event.action != Action.RIGHT_CLICK_AIR) return
        if (item == null) return

        val isShootableFireball = PDCUtil.get(
            item,
            PDCEntryUtil.PDCKey(plugin).itemIdentifierKey,
            PersistentDataType.STRING
        ) == PDCEntryUtil.PDCValue().shootableFireballItemIdentifier

        if (!isShootableFireball) return

        event.isCancelled = true

        val now = System.currentTimeMillis()
        val last = lastShotAt[player.uniqueId] ?: 0L
        if (now - last < cooldownMillis) return
        lastShotAt[player.uniqueId] = now

        val direction = player.location.direction.normalize()
        val spawnLoc = player.eyeLocation.add(direction.clone().multiply(0.5))

        val fireball = player.world.spawn(spawnLoc, Fireball::class.java)
        fireball.shooter = player
        fireball.direction = direction
        fireball.velocity = direction.multiply(1.6)
        fireball.yield = 1.0f

        if (player.gameMode == GameMode.CREATIVE) return
        val usedItem = event.item ?: return
        val newAmount = usedItem.amount - 1
        if (newAmount <= 0) {
            event.player.inventory.setItem(event.hand!!, null)
        } else {
            usedItem.amount = newAmount
        }
    }
}