package net.trilleo.mc.plugins.trihunt.items.utilityItems

import net.trilleo.mc.plugins.trihunt.registration.PluginItem
import net.trilleo.mc.plugins.trihunt.utils.PDCEntryUtil
import net.trilleo.mc.plugins.trihunt.utils.itemStack
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin

class BridgeEggItem(private val plugin: JavaPlugin) : PluginItem("bridge-egg") {
    override fun buildItem(amount: Int): ItemStack = itemStack(Material.EGG) {
        name("<#a53860>Bridge Egg")
        lore(
            "<gray>A throwable egg that creates a bridge.",
            "<gray>Useful for crossing gaps!"
        )
        enchant(Enchantment.DENSITY, 1)
        flag(ItemFlag.HIDE_ENCHANTS)
        pdc(
            PDCEntryUtil.PDCKey(plugin).itemIdentifierKey,
            PersistentDataType.STRING,
            PDCEntryUtil.PDCValue().bridgeEggItemIdentifier
        )
    }
}