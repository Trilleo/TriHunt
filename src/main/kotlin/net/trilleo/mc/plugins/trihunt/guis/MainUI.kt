package net.trilleo.mc.plugins.trihunt.guis

import net.kyori.adventure.text.Component
import net.trilleo.mc.plugins.trihunt.registration.PluginGUI
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory

class MainUI : PluginGUI(
    id = "main",
    title = Component.text { "TriHunt Main UI" },
    rows = 5
) {
    override fun setup(player: Player, inventory: Inventory) {
    }

    override fun onClick(event: InventoryClickEvent) {
    }
}