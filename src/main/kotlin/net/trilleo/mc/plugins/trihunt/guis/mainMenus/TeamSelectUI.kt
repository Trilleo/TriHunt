package net.trilleo.mc.plugins.trihunt.guis.mainMenus

import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.trilleo.mc.plugins.trihunt.enums.FillMode
import net.trilleo.mc.plugins.trihunt.registration.GUIManager
import net.trilleo.mc.plugins.trihunt.registration.PluginGUI
import net.trilleo.mc.plugins.trihunt.utils.TeamUtil
import net.trilleo.mc.plugins.trihunt.utils.itemStack
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory

class TeamSelectUI : PluginGUI(
    id = "team-select",
    title = Component.text("Team").color(NamedTextColor.DARK_BLUE).decorate(TextDecoration.BOLD),
    rows = 6,
    fillMode = FillMode.LIGHT
) {
    val slotIndex: Map<String, Int> = mapOf(
        "backButtonSlot" to 48,
        "closeButtonSlot" to 49
    )
    val infoIndex: Map<String, Int> = mapOf(
        "infoButtonSlot" to 13
    )

    fun refreshInventory(player: Player, inventory: Inventory) {
        val infoButton = itemStack(Material.BOOK) {
            name("<bold><white>Select your team")
            lore(
                "   ",
                "<white>Current Team: ${TeamUtil.getPlayerTeam(player)?.displayName ?: "<dark_gray>None"}"
            )
        }

        inventory.setItem(infoIndex.getValue("infoButtonSlot"), infoButton)
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

        refreshInventory(player, inventory)
    }

    override fun onClick(event: InventoryClickEvent) {
        event.isCancelled = true
        val player = event.whoClicked as Player
        if (event.slot in slotIndex.values) {
            player.playSound(
                Sound.sound(Key.key("minecraft:ui.button.click"), Sound.Source.UI, 1f, 1f)
            )
        }

        if (event.slot == slotIndex.getValue("closeButtonSlot")) {
            player.closeInventory()
        }
        if (event.slot == slotIndex.getValue("backButtonSlot")) {
            GUIManager.open(player, "main")
        }
    }
}