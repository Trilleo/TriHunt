package net.trilleo.mc.plugins.trihunt.listeners.item

import net.trilleo.mc.plugins.trihunt.utils.PDCEntryUtil
import net.trilleo.mc.plugins.trihunt.utils.PDCUtil
import org.bukkit.GameMode
import org.bukkit.entity.TNTPrimed
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class ThrowableTNTListener(private val plugin: JavaPlugin) : Listener {

    private val cooldownMillis = 1000L
    private val lastThrownAt = mutableMapOf<UUID, Long>()

    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        val player = event.player
        val item = event.item

        if (event.action != Action.RIGHT_CLICK_BLOCK && event.action != Action.RIGHT_CLICK_AIR) return
        if (item == null) return

        val isThrowableTNT = PDCUtil.get(
            item,
            PDCEntryUtil.PDCKey(plugin).itemIdentifierKey,
            PersistentDataType.STRING
        ) == PDCEntryUtil.PDCValue().throwableTNTItemIdentifier

        if (!isThrowableTNT) return

        event.isCancelled = true

        val now = System.currentTimeMillis()
        val last = lastThrownAt[player.uniqueId] ?: 0L
        if (now - last < cooldownMillis) return
        lastThrownAt[player.uniqueId] = now

        val direction = player.location.direction.normalize()
        val spawnLoc = player.eyeLocation

        val tnt = player.world.spawn(spawnLoc, TNTPrimed::class.java)
        tnt.velocity = direction.multiply(0.8)
        tnt.source = player
        tnt.fuseTicks = 60

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