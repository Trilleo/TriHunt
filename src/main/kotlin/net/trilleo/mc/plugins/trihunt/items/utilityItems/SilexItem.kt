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

class SilexItem(private val plugin: JavaPlugin) : PluginItem("silex") {
    override fun buildItem(amount: Int): ItemStack = itemStack(Material.FLINT) {
        name("<#a53860>Silex")
        lore(
            "<dark_gray>Combinable in Anvil",
            "   ",
            "<gray>A crystal that allows your pickaxe",
            "<gray>to break multiple blocks at once."
        )
        enchant(Enchantment.DENSITY, 1)
        flag(ItemFlag.HIDE_ENCHANTS)
        pdc(
            PDCEntryUtil.PDCKey(plugin).itemIdentifierKey,
            PersistentDataType.STRING,
            PDCEntryUtil.PDCValue().silexItemIdentifier
        )
    }
}