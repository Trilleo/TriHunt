package net.trilleo.mc.plugins.trihunt.listeners.item

import net.trilleo.mc.plugins.trihunt.utils.PDCEntryUtil
import net.trilleo.mc.plugins.trihunt.utils.PDCUtil
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.block.data.BlockData
import org.bukkit.entity.Egg
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.ProjectileLaunchEvent
import org.bukkit.event.player.PlayerEggThrowEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.BlockIterator

class BridgeEggListener(private val plugin: JavaPlugin) : Listener {

    private val maxDistance = Int.MAX_VALUE
    private val placedBlockData: BlockData = Bukkit.createBlockData(Material.STONE)

    @EventHandler
    fun onEggLaunch(event: ProjectileLaunchEvent) {
        val projectile = event.entity as? Egg ?: return
        val shooter = projectile.shooter as? Player ?: return
        val item = projectile.item

        if (!isBridgeEgg(item)) return

        BridgePlacerTask(
            egg = projectile,
            startLocation = shooter.location,
            maxDistance = maxDistance,
            blockData = placedBlockData
        ).begin()
    }

    @EventHandler
    fun onEggThrow(event: PlayerEggThrowEvent) {
        if (isBridgeEgg(event.egg.item)) {
            event.isHatching = false
        }
    }

    private fun isBridgeEgg(item: ItemStack?): Boolean {
        if (item == null || item.type != Material.EGG) return false

        return PDCUtil.get(
            item,
            PDCEntryUtil.PDCKey(plugin).itemIdentifierKey,
            PersistentDataType.STRING
        ) == PDCEntryUtil.PDCValue().bridgeEggItemIdentifier
    }

    private inner class BridgePlacerTask(
        private val egg: Egg,
        private val startLocation: Location,
        private val maxDistance: Int,
        private val blockData: BlockData
    ) : BukkitRunnable() {

        private var lastLocation: Location? = null

        fun begin() {
            runTaskTimer(plugin, 2L, 1L)
        }

        override fun run() {
            if (egg.isDead || !egg.isValid) {
                cancel()
                return
            }

            val twoBlocksDown = egg.location.clone().subtract(0.0, 2.0, 0.0)
            val distanceFromStart = twoBlocksDown.distance(startLocation)

            if (lastLocation == null) {
                lastLocation = twoBlocksDown.clone()
            }

            if (distanceFromStart < maxDistance) {
                scheduleSegmentPlace(twoBlocksDown)
            } else {
                egg.remove()
                cancel()
            }

            lastLocation = twoBlocksDown
        }

        private fun scheduleSegmentPlace(location: Location) {
            Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                placeSegment(location)
            }, 5L)
        }

        private fun placeSegment(location: Location) {
            val iterator = BlockIterator(location, 0.0, 1)
            iterator.forEachRemaining { block -> setData(block) }

            location.world?.playSound(location, Sound.BLOCK_LAVA_POP, 2.0f, 3.0f)
        }

        private fun setData(block: Block) {
            if (!block.type.isSolid && isWithinHeight(block.location.y, block.world.minHeight, block.world.maxHeight)) {
                block.blockData = blockData
            }
        }

        private fun isWithinHeight(y: Double, min: Int, max: Int): Boolean {
            val blockY = y.toInt()
            return blockY >= min && blockY < max
        }
    }
}