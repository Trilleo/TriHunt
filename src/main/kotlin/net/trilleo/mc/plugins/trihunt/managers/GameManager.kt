package net.trilleo.mc.plugins.trihunt.managers

import net.trilleo.mc.plugins.trihunt.data.ServerDataManager
import net.trilleo.mc.plugins.trihunt.utils.TeamUtil
import net.trilleo.mc.plugins.trihunt.utils.sendPrefixed
import org.bukkit.entity.Player

object GameManager {
    fun checkCondition(player: Player): Boolean {
        if (TeamUtil.getTeam("speedrunner")?.memberCount == 0) {
            player.sendPrefixed("<dark_red>There must be at least 1 player in ${TeamUtil.getTeam("speedrunner")?.displayName}")
            return false
        }
        if (TeamUtil.getTeam("hunter")?.memberCount == 0) {
            player.sendPrefixed("<dark_red>There must be at least 1 player in ${TeamUtil.getTeam("hunter")?.displayName}")
            return false
        }
        return true
    }

    fun initiateGame() {
        val serverData = ServerDataManager.get()

        serverData.set("gameStatus", "inactive")
    }

    fun prepareGame() {
        val serverData = ServerDataManager.get()

        serverData.set("gameStatus", "ready")
    }

    fun updateMainItem(player: Player) {
        val serverData = ServerDataManager.get()
    }
}