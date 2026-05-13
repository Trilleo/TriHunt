package net.trilleo.mc.plugins.trihunt.items.pluginItems

import net.trilleo.mc.plugins.trihunt.registration.PluginItem
import net.trilleo.mc.plugins.trihunt.utils.PDCEntryUtil
import net.trilleo.mc.plugins.trihunt.utils.itemStack
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin

class AssassinSwordItem(private val plugin: JavaPlugin) : PluginItem("assassin-sword") {
    override fun buildItem(amount: Int): ItemStack = itemStack(Material.DIAMOND_SWORD) {
        name("<dark_red>Assassin Sword")
        lore(
            "<gray>ONE HIT!"
        )
        enchant(Enchantment.DENSITY, 1)
        flag(ItemFlag.HIDE_ENCHANTS)
        pdc(
            PDCEntryUtil.PDCKey(plugin).itemIdentifierKey,
            PersistentDataType.STRING,
            PDCEntryUtil.PDCValue().assassinSwordItemIdentifier
        )
    }
}