package net.trilleo.mc.plugins.trihunt.listeners.item

import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.trilleo.mc.plugins.trihunt.utils.PDCEntryUtil
import net.trilleo.mc.plugins.trihunt.utils.PDCUtil
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.util.UUID

class GoldenHeadListener(private val plugin: JavaPlugin) : Listener {

    private val cooldownMillis = 1000L
    private val lastConsumeAt = mutableMapOf<UUID, Long>()

    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        val player = event.player
        val item = event.item

        if (event.action == Action.RIGHT_CLICK_BLOCK || event.action == Action.RIGHT_CLICK_AIR) {
            if (item != null && PDCUtil.get(
                    item,
                    PDCEntryUtil.PDCKey(plugin).itemIdentifierKey,
                    PersistentDataType.STRING
                ) == PDCEntryUtil.PDCValue().goldenHeadItemIdentifier
            ) {
                event.isCancelled = true

                val now = System.currentTimeMillis()
                val last = lastConsumeAt[player.uniqueId] ?: 0L
                if (now - last < cooldownMillis) return
                lastConsumeAt[player.uniqueId] = now

                player.playSound(Sound.sound(Key.key("minecraft:entity.player.burp"), Sound.Source.MASTER, 1.0f, 1.0f))

                val healthRegenEffect = PotionEffect(
                    PotionEffectType.REGENERATION,
                    100,
                    2,
                    false,
                    false
                )
                val absorptionEffect = PotionEffect(
                    PotionEffectType.ABSORPTION,
                    1200,
                    3,
                    false,
                    false
                )
                val strengthEffect = PotionEffect(
                    PotionEffectType.STRENGTH,
                    1200,
                    1,
                    false,
                    false
                )
                val saturationEffect = PotionEffect(
                    PotionEffectType.SATURATION,
                    100,
                    1,
                    false,
                    false
                )

                player.addPotionEffect(healthRegenEffect)
                player.addPotionEffect(absorptionEffect)
                player.addPotionEffect(strengthEffect)
                player.addPotionEffect(saturationEffect)

                if (player.gameMode == GameMode.CREATIVE) return
                val usedItem = event.item ?: return
                val newAmount = usedItem.amount - 1
                if (newAmount <= 0) {
                    event.player.inventory.setItem(event.hand!!, null)
                } else {
                    usedItem.amount = newAmount
                }
            }
        }
    }
}