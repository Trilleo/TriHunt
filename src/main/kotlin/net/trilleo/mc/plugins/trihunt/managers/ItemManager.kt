package net.trilleo.mc.plugins.trihunt.managers

import net.trilleo.mc.plugins.trihunt.utils.PDCEntryUtil
import net.trilleo.mc.plugins.trihunt.utils.PDCUtil
import net.trilleo.mc.plugins.trihunt.utils.itemStack
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin

class ItemManager(private val plugin: JavaPlugin) {
    fun createMainItem(): ItemStack {
        val mainItem = itemStack(Material.NETHER_STAR) {
            name("<bold><gold>TriHunt Menu")
            lore(
                "   ",
                "<gray>[Right Click] to open menu"
            )
            enchant(Enchantment.BINDING_CURSE, 1)
            flag(ItemFlag.HIDE_ENCHANTS)
            pdc(
                PDCEntryUtil.PDCKey(plugin).itemIdentifierKey,
                PersistentDataType.STRING,
                PDCEntryUtil.PDCValue().mainItemIdentifier
            )
        }
        return mainItem
    }

    fun createCompassItem(): ItemStack {
        val compassItem = itemStack(Material.COMPASS) {
            name("<green>Tracking Compass")
            enchant(Enchantment.DENSITY, 1)
            flag(ItemFlag.HIDE_ENCHANTS)
            pdc(
                PDCEntryUtil.PDCKey(plugin).itemIdentifierKey,
                PersistentDataType.STRING,
                PDCEntryUtil.PDCValue().compassItemIdentifier
            )
        }
        return compassItem
    }

    fun clearPluginItems(player: Player) {
        for (item in player.inventory.contents) {
            if (item != null && PDCUtil.get(
                    item,
                    PDCEntryUtil.PDCKey(plugin).itemIdentifierKey,
                    PersistentDataType.STRING
                ) in listOf(
                    PDCEntryUtil.PDCValue().mainItemIdentifier,
                    PDCEntryUtil.PDCValue().compassItemIdentifier
                )
            ) {
                player.inventory.remove(item)
            }
        }
    }
}