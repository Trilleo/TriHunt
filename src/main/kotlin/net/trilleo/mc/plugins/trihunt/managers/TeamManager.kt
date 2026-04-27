package net.trilleo.mc.plugins.trihunt.managers

import net.trilleo.mc.plugins.trihunt.utils.TeamUtil

object TeamManager {
    fun initializeTeam() {
        TeamUtil.createTeam("speedrunner", "<bold><green>Speedrunner")
        TeamUtil.createTeam("hunter", "<bold><red>Hunter")
        TeamUtil.createTeam("Spectator", "<bold><gray>Spectator")
    }
}