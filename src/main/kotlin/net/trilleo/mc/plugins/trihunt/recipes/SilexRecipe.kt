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
            "III",
            "IFI",
            "III"
        )

        recipe.setIngredient('I', vanillaChoice(Material.IRON_INGOT))
        recipe.setIngredient('F', vanillaChoice(Material.FLINT))

        return recipe
    }
}