package net.trilleo.mc.plugins.trihunt.recipes

import net.trilleo.mc.plugins.trihunt.items.utilityItems.SilexItem
import net.trilleo.mc.plugins.trihunt.registration.PluginRecipe
import org.bukkit.Material
import org.bukkit.inventory.Recipe
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.plugin.java.JavaPlugin

class SilexRecipe : PluginRecipe("silex-craft") {
    override fun build(plugin: JavaPlugin): Recipe {
        val recipe = ShapedRecipe(namespacedKey(plugin), SilexItem(plugin).create())
        recipe.shape(
            "DGD",
            "GFG",
            "DGD"
        )

        recipe.setIngredient('F', vanillaChoice(Material.FLINT))
        recipe.setIngredient('D', Material.DIAMOND)
        recipe.setIngredient('G', Material.GOLD_INGOT)

        return recipe
    }
}