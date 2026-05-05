package net.trilleo.mc.plugins.trihunt.utils

import org.bukkit.NamespacedKey
import org.bukkit.plugin.java.JavaPlugin

class PDCEntryUtil {
    // Namespaced Keys
    class PDCKey(private val plugin: JavaPlugin) {
        // Key for identifying plugin items
        val itemIdentifierKey = NamespacedKey(plugin, "itemIdentifier")
    }

    // Values
    class PDCValue {
        // itemIdentifier - Main Item
        val mainItemIdentifier = "main-item"

        // itemIdentifier - Hunter Compass
        val compassItemIdentifier = "hunter-compass"

        // itemIdentifier - Bridge Egg
        val bridgeEggItemIdentifier = "bridge-egg"

        // itemIdentifier - Shootable Fireball
        val shootableFireballItemIdentifier = "shootable-fireball"

        // itemIdentifier - Golden Head
        val goldenHeadItemIdentifier = "golden-head"
    }
}