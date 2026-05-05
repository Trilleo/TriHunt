package net.trilleo.mc.plugins.trihunt.guis.configMenus

import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.trilleo.mc.plugins.trihunt.data.ServerDataManager
import net.trilleo.mc.plugins.trihunt.enums.FillMode
import net.trilleo.mc.plugins.trihunt.registration.GUIManager
import net.trilleo.mc.plugins.trihunt.registration.PluginGUI
import net.trilleo.mc.plugins.trihunt.registration.RecipeRegistrar
import net.trilleo.mc.plugins.trihunt.utils.itemStack
import net.trilleo.mc.plugins.trihunt.utils.sendPrefixed
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemFlag
import org.bukkit.plugin.java.JavaPlugin

class SettingsUI(private val plugin: JavaPlugin) : PluginGUI(
    id = "settings",
    title = Component.text("TriHunt Settings").color(NamedTextColor.DARK_GRAY).decorate(TextDecoration.BOLD),
    rows = 6,
    fillMode = FillMode.LIGHT
) {
    val slotIndex: Map<String, Int> = mapOf(
        "backButtonSlot" to 48,
        "closeButtonSlot" to 49,
        "gameRuleButtonSlot" to 53,
        "gameModeButtonSlot" to 45
    )
    val settingsIndex: Map<String, Int> = mapOf(
        "autoRefreshCompass" to 10,
        "speedrunnerBonusTime" to 12,
        "customItems" to 14
    )

    fun refreshSettings(inventory: Inventory) {
        val serverData = ServerDataManager.get()

        val isAutoRefreshCompass = serverData.getBoolean("autoRefreshCompass", false)
        val autoRefreshCompassButton = itemStack(Material.COMPASS) {
            name("<bold><white>Compass Refresh")
            lore(
                "   ",
                "<gray>Automatically refresh compass every 30 seconds",
                "   ",
                "<white>Value: <yellow>$isAutoRefreshCompass",
                "   ",
                "<gray>[Click] <dark_gray>to toggle"
            )
        }

        val speedrunnerBonusTime = serverData.getInt("speedrunnerBonusTime", 10)
        val speedrunnerBonusTimeButton = itemStack(Material.CLOCK) {
            name("<bold><white>Speedrunner Bonus Time")
            lore(
                "   ",
                "<gray>Time for speedrunner to escape",
                "<gray>before hunters are released (seconds)",
                "   ",
                "<white>Value: <yellow>$speedrunnerBonusTime",
                "   ",
                "<gray>[Left Click] <dark_gray>to decrease by 1",
                "   ",
                "<gray>[Right Click] <dark_gray>to increase by 1"
            )
        }
        val isCustomItems = serverData.getBoolean("customItems", true)
        val customItemsButton = itemStack(Material.CRAFTING_TABLE) {
            name("<bold><white>Custom Items / Recipes")
            lore(
                "   ",
                "<gray>Register custom items and crafting recipes",
                "   ",
                "<white>Value: <yellow>$isCustomItems",
                "   ",
                "<gray>[Click] <dark_gray>to toggle"
            )
        }

        inventory.setItem(settingsIndex.getValue("autoRefreshCompass"), autoRefreshCompassButton)
        inventory.setItem(settingsIndex.getValue("speedrunnerBonusTime"), speedrunnerBonusTimeButton)
        inventory.setItem(settingsIndex.getValue("customItems"), customItemsButton)
    }

    override fun setup(player: Player, inventory: Inventory) {
        val closeButton = itemStack(Material.BARRIER) {
            name("<bold><red>Close")
        }
        val backButton = itemStack(Material.ARROW) {
            name("<bold><gray>Back")
        }
        val gameRuleButton = itemStack(Material.GRASS_BLOCK) {
            name("<bold><yellow>GameRule Modifier")
            lore(
                "   ",
                "<gray>Change Minecraft game rules"
            )
        }
        val gameModeButton = itemStack(Material.DIAMOND_SPEAR) {
            name("<bold><dark_red>TriHunt Modes")
            lore(
                "   ",
                "<gray>Change TriHunt game modes"
            )
            flag(ItemFlag.HIDE_ATTRIBUTES)
        }

        inventory.setItem(slotIndex.getValue("backButtonSlot"), backButton)
        inventory.setItem(slotIndex.getValue("closeButtonSlot"), closeButton)
        inventory.setItem(slotIndex.getValue("gameRuleButtonSlot"), gameRuleButton)
        inventory.setItem(slotIndex.getValue("gameModeButtonSlot"), gameModeButton)

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
        if (event.slot == slotIndex.getValue("gameRuleButtonSlot")) {
            GUIManager.open(player, "gamerule")
        }
        if (event.slot == slotIndex.getValue("gameModeButtonSlot")) {
            GUIManager.open(player, "gamemode")
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
        if (event.slot == settingsIndex.getValue("speedrunnerBonusTime")) {
            val speedrunnerBonusTime = serverData.getInt("speedrunnerBonusTime", 10)

            if (event.isLeftClick) {
                if (speedrunnerBonusTime == 0) {
                    player.sendPrefixed("<dark_red>Time cannot be less than 0!")
                    return
                }

                serverData.set("speedrunnerBonusTime", speedrunnerBonusTime - 1)
            }
            if (event.isRightClick) {
                if (speedrunnerBonusTime == 120) {
                    player.sendPrefixed("<dark_red>Time cannot be more than 120!")
                    return
                }

                serverData.set("speedrunnerBonusTime", speedrunnerBonusTime + 1)
            }

            refreshSettings(event.inventory)
        }
        if (event.slot == settingsIndex.getValue("customItems")) {
            val isCustomItems = serverData.getBoolean("customItems", true)

            if (isCustomItems) {
                serverData.set("customItems", false)
                RecipeRegistrar.unregisterAll()
            } else {
                serverData.set("customItems", true)
                RecipeRegistrar.registerAll(plugin)
            }

            refreshSettings(event.inventory)
        }
    }
}