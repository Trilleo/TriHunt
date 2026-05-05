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

class ShootableFireballItem(private val plugin: JavaPlugin) : PluginItem("shootable-fireball") {
    override fun buildItem(amount: Int): ItemStack = itemStack(Material.FIRE_CHARGE) {
        name("<@a53860>Shootable Fireball")
        lore(
            "<gray>A fire charge that shoots a fireball when used.",
            "<gray>Useful for attacking or lighting up areas!"
        )
        enchant(Enchantment.DENSITY, 1)
        flag(ItemFlag.HIDE_ENCHANTS)
        pdc(
            PDCEntryUtil.PDCKey(plugin).itemIdentifierKey,
            PersistentDataType.STRING,
            PDCEntryUtil.PDCValue().shootableFireballItemIdentifier
        )
    }
}