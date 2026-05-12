package net.trilleo.mc.plugins.trihunt.items.utilityItems

import net.trilleo.mc.plugins.trihunt.registration.PluginItem
import net.trilleo.mc.plugins.trihunt.utils.PDCEntryUtil
import net.trilleo.mc.plugins.trihunt.utils.itemStack
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class KnockbackStickItem(private val plugin: JavaPlugin) : PluginItem("knockback-stick") {
    override fun buildItem(amount: Int): ItemStack = itemStack(Material.STICK) {
        name("<#a53860>Knockback Stick")
        lore(
            "<dark_gray>Consumed on Use",
            "   ",
            "<gray>A stick that applies a strong knockback effect to the target when used.",
            "<gray>Useful for pushing enemies away or creating distance!"
        )
        enchant(Enchantment.KNOCKBACK, 5)
        pdc(
            PDCEntryUtil.PDCKey(plugin).itemIdentifierKey,
            org.bukkit.persistence.PersistentDataType.STRING,
            PDCEntryUtil.PDCValue().knockbackStickItemIdentifier
        )
    }
}