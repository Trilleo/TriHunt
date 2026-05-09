package net.trilleo.mc.plugins.trihunt.listeners.item

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.trilleo.mc.plugins.trihunt.utils.PDCEntryUtil
import net.trilleo.mc.plugins.trihunt.utils.PDCUtil
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.PrepareAnvilEvent
import org.bukkit.inventory.AnvilInventory
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin

class SilexListener(private val plugin: JavaPlugin) : Listener {
    // Detect Anvil combine
    @EventHandler
    fun onPrepareAnvil(event: PrepareAnvilEvent) {
        val inventory = event.inventory
        val baseItem = inventory.getItem(0) ?: return
        val upgradeItem = inventory.getItem(1) ?: return

        val pickaxes = listOf(
            Material.WOODEN_PICKAXE,
            Material.STONE_PICKAXE,
            Material.IRON_PICKAXE,
            Material.GOLDEN_PICKAXE,
            Material.DIAMOND_PICKAXE,
            Material.NETHERITE_PICKAXE
        )

        if (PDCUtil.get(
                upgradeItem,
                PDCEntryUtil.PDCKey(plugin).itemIdentifierKey,
                PersistentDataType.STRING
            ) == PDCEntryUtil.PDCValue().silexItemIdentifier
        ) {
            if (baseItem.type in pickaxes) {
                val result = baseItem.clone()

                PDCUtil.set(
                    result,
                    PDCEntryUtil.PDCKey(plugin).silexEnrichedItemIdentifierKey,
                    PersistentDataType.BOOLEAN,
                    PDCEntryUtil.PDCValue().isSilexEnrichedItemIdentifier
                )

                val resultMeta = result.itemMeta
                val existingLore = resultMeta.lore() ?: emptyList()
                val newLore = existingLore.toMutableList()
                if (existingLore.isNotEmpty()) newLore.add(Component.empty())
                newLore.add(
                    Component.text("Enriched with Silex").color(NamedTextColor.DARK_GRAY).decoration(
                        TextDecoration.ITALIC, false
                    )
                )
                resultMeta.lore(newLore)
                result.itemMeta = resultMeta

                event.result = result
            }
        }
    }

    // Detect Anvil click
    @EventHandler
    fun onAnvilTake(event: InventoryClickEvent) {
        val inv = event.inventory
        val player = event.whoClicked as Player
        if (inv !is AnvilInventory) return
        if (event.rawSlot != 2) return
        val result = inv.getItem(2) ?: return

        if (PDCUtil.get(
                result,
                PDCEntryUtil.PDCKey(plugin).silexEnrichedItemIdentifierKey,
                PersistentDataType.BOOLEAN
            ) == false
        ) return

        val upgradeSlot = inv.getItem(1) ?: return
        Bukkit.getScheduler().runTask(plugin, Runnable {
            if (upgradeSlot.amount <= 1) inv.setItem(1, null)
            else upgradeSlot.amount -= 1
            inv.setItem(0, null)
        })

        player.inventory.addItem(result)
    }

}