package net.trilleo.mc.plugins.trihunt.managers

import net.trilleo.mc.plugins.trihunt.utils.PDCEntryUtil
import net.trilleo.mc.plugins.trihunt.utils.itemStack
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
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
}