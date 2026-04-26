package net.trilleo.mc.plugins.trihunt.guis

import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.trilleo.mc.plugins.trihunt.data.ServerDataManager
import net.trilleo.mc.plugins.trihunt.enums.FillMode
import net.trilleo.mc.plugins.trihunt.registration.GUIManager
import net.trilleo.mc.plugins.trihunt.registration.PluginGUI
import net.trilleo.mc.plugins.trihunt.utils.itemStack
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory

class SettingsUI : PluginGUI(
    id = "settings",
    title = Component.text("TriHunt Settings").color(NamedTextColor.DARK_GRAY).decorate(TextDecoration.BOLD),
    rows = 6,
    fillMode = FillMode.LIGHT
) {
    val slotIndex: Map<String, Int> = mapOf(
        "backButtonSlot" to 45,
        "closeButtonSlot" to 49
    )
    val settingsIndex: Map<String, Int> = mapOf(
        "autoRefreshCompass" to 10
    )

    fun refreshSettings(inventory: Inventory) {
        val serverData = ServerDataManager.get()

        val isAutoRefreshCompass = serverData.getBoolean("autoRefreshCompass", false)
        val autoRefreshCompassButton = itemStack(Material.COMPASS) {
            name("<bold><white>Compass Refresh")
            lore(
                "   ",
                "<gray>Automatically refresh compass every 5 seconds",
                "   ",
                "<dark_gray>Value: <yellow>$isAutoRefreshCompass"
            )
        }

        inventory.setItem(settingsIndex.getValue("autoRefreshCompass"), autoRefreshCompassButton)
    }

    override fun setup(player: Player, inventory: Inventory) {
        val closeButton = itemStack(Material.BARRIER) {
            name("<bold><red>Close")
        }
        val backButton = itemStack(Material.ARROW) {
            name("<bold><gray>Back")
        }

        inventory.setItem(slotIndex.getValue("backButtonSlot"), backButton)
        inventory.setItem(slotIndex.getValue("closeButtonSlot"), closeButton)

        refreshSettings(inventory)
    }

    override fun onClick(event: InventoryClickEvent) {
        event.isCancelled = true
        val player = event.whoClicked as Player
        if (event.slot in slotIndex.values) {
            player.playSound(
                Sound.sound(Key.key("minecraft:ui.button.click"), Sound.Source.UI, 1f, 1f)
            )
        }
        if (event.slot in settingsIndex.values) {
            player.playSound(
                Sound.sound(Key.key("minecraft:entity.experience_orb.pickup"), Sound.Source.UI, 1f, 1f)
            )
        }

        val serverData = ServerDataManager.get()

        if (event.slot == slotIndex.getValue("closeButtonSlot")) {
            player.closeInventory()
        }
        if (event.slot == slotIndex.getValue("backButtonSlot")) {
            GUIManager.open(player, "main")
        }

        if (event.slot == settingsIndex.getValue("autoRefreshCompass")) {
            val isAutoRefreshCompass = serverData.getBoolean("autoRefreshCompass", false)

            if (isAutoRefreshCompass) {
                serverData.set("autoRefreshCompass", false)
            } else {
                serverData.set("autoRefreshCompass", true)
            }

            refreshSettings(event.inventory)
        }
    }
}