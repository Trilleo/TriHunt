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

class ThrowableTNTItem(private val plugin: JavaPlugin) : PluginItem("throwable-tnt") {
    override fun buildItem(amount: Int): ItemStack = itemStack(Material.TNT) {
        name("<#a53860>Throwable TNT")
        lore(
            "<gray>A piece of TNT that can be thrown to explode on impact.",
            "<gray>Great for surprise attacks or clearing out areas!"
        )
        enchant(Enchantment.DENSITY, 1)
        flag(ItemFlag.HIDE_ENCHANTS)
        pdc(
            PDCEntryUtil.PDCKey(plugin).itemIdentifierKey,
            PersistentDataType.STRING,
            PDCEntryUtil.PDCValue().throwableTNTItemIdentifier
        )
    }
}