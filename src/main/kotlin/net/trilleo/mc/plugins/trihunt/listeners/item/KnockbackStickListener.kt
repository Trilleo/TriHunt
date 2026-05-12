package net.trilleo.mc.plugins.trihunt.listeners.item

import net.trilleo.mc.plugins.trihunt.utils.PDCEntryUtil
import net.trilleo.mc.plugins.trihunt.utils.PDCUtil
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin

class KnockbackStickListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onDamage(event: EntityDamageByEntityEvent) {
        val damager = event.damager as? Player ?: return
        val item = damager.inventory.itemInMainHand

        if (PDCUtil.get(
                item,
                PDCEntryUtil.PDCKey(plugin).itemIdentifierKey,
                PersistentDataType.STRING
            ) == PDCEntryUtil.PDCValue().knockbackStickItemIdentifier
        ) {
            if (damager.gameMode == GameMode.CREATIVE) return
            val newAmount = item.amount - 1
            item.amount = newAmount
        }
    }
}