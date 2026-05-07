package net.trilleo.mc.plugins.trihunt.recipes

import net.trilleo.mc.plugins.trihunt.items.utilityItems.GoldenHeadItem
import net.trilleo.mc.plugins.trihunt.registration.PluginRecipe
import org.bukkit.Material
import org.bukkit.inventory.Recipe
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.plugin.java.JavaPlugin

class GoldenHeadRecipe : PluginRecipe("golden-head-craft") {
    override fun build(plugin: JavaPlugin): Recipe {
        val recipe = ShapedRecipe(namespacedKey(plugin), GoldenHeadItem(plugin).create())
        recipe.shape(
            " G ",
            "GPG",
            " G "
        )

        recipe.setIngredient('G', vanillaChoice(Material.GOLD_INGOT))
        recipe.setIngredient('P', vanillaChoice(Material.PLAYER_HEAD))

        return recipe
    }
}