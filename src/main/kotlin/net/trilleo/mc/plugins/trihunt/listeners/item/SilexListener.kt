package net.trilleo.mc.plugins.trihunt.listeners.item

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.trilleo.mc.plugins.trihunt.utils.PDCEntryUtil
import net.trilleo.mc.plugins.trihunt.utils.PDCUtil
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.PrepareAnvilEvent
import org.bukkit.inventory.AnvilInventory
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

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
        val upgradeItem = inv.getItem(1) ?: return
        if (event.rawSlot != 2) return
        val result = inv.getItem(2) ?: return

        if (PDCUtil.get(
                upgradeItem,
                PDCEntryUtil.PDCKey(plugin).itemIdentifierKey,
                PersistentDataType.STRING
            ) != PDCEntryUtil.PDCValue().silexItemIdentifier
        ) return
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

    private val blockList = listOf(
        Material.STONE,
        Material.COAL_ORE,
        Material.IRON_ORE,
        Material.GOLD_ORE,
        Material.DIAMOND_ORE,
        Material.EMERALD_ORE,
        Material.REDSTONE_ORE,
        Material.LAPIS_ORE,
        Material.COPPER_ORE,
        Material.DEEPSLATE,
        Material.DEEPSLATE_COAL_ORE,
        Material.DEEPSLATE_IRON_ORE,
        Material.DEEPSLATE_GOLD_ORE,
        Material.DEEPSLATE_DIAMOND_ORE,
        Material.DEEPSLATE_EMERALD_ORE,
        Material.DEEPSLATE_REDSTONE_ORE,
        Material.DEEPSLATE_LAPIS_ORE,
        Material.DEEPSLATE_COPPER_ORE,
        Material.NETHERRACK,
        Material.NETHER_GOLD_ORE,
        Material.NETHER_QUARTZ_ORE,
        Material.ANCIENT_DEBRIS,
        Material.DIORITE,
        Material.ANDESITE
    )

    private val cooldownMillis = 100L
    private val lastBrokeAt = mutableMapOf<UUID, Long>()

    private fun breakAdjacent(player: Player, block: Block) {
        val world = block.world
        val blockType = block.type

        for (dx in -1..1) {
            for (dy in -1..1) {
                for (dz in -1..1) {
                    if (dx == 0 && dy == 0 && dz == 0) return
                    val adjacentBlock = world.getBlockAt(
                        block.x + dx,
                        block.y + dy,
                        block.z + dz
                    )
                    if (adjacentBlock.type == blockType) {
                        adjacentBlock.breakNaturally(player.inventory.itemInMainHand)
                    }
                }
            }
        }
    }

    // Detect block break
    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        val player = event.player
        val block = event.block
        val item = player.inventory.itemInMainHand
        if (player.gameMode == GameMode.CREATIVE) return

        val now = System.currentTimeMillis()
        val last = lastBrokeAt[player.uniqueId] ?: 0L
        if (now - last < cooldownMillis) {
            event.isCancelled = true
            return
        }
        lastBrokeAt[player.uniqueId] = now

        if (block.type in blockList && PDCUtil.get(
                item,
                PDCEntryUtil.PDCKey(plugin).silexEnrichedItemIdentifierKey,
                PersistentDataType.BOOLEAN
            ) == true
        ) {
            breakAdjacent(player, block)
        }
    }
}