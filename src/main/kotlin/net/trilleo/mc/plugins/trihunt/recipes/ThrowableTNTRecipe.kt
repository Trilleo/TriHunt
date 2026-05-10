package net.trilleo.mc.plugins.trihunt.recipes

import net.trilleo.mc.plugins.trihunt.items.utilityItems.ThrowableTNTItem
import net.trilleo.mc.plugins.trihunt.registration.PluginRecipe
import org.bukkit.Material
import org.bukkit.inventory.Recipe
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.plugin.java.JavaPlugin

class ThrowableTNTRecipe : PluginRecipe("throwable-tnt-craft") {
    override fun build(plugin: JavaPlugin): Recipe {
        val recipe = ShapedRecipe(namespacedKey(plugin), ThrowableTNTItem(plugin).create())
        recipe.shape(
            " G ",
            " T ",
            " R "
        )

        recipe.setIngredient('G', Material.GOLD_INGOT)
        recipe.setIngredient('T', Material.TNT)
        recipe.setIngredient('R', Material.REDSTONE_TORCH)

        return recipe
    }
}