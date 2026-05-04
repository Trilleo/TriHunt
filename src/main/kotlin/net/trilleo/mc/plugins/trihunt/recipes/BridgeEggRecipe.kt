package net.trilleo.mc.plugins.trihunt.recipes

import net.trilleo.mc.plugins.trihunt.items.BridgeEggItem
import net.trilleo.mc.plugins.trihunt.registration.PluginRecipe
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.Recipe
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.plugin.java.JavaPlugin

class BridgeEggRecipe : PluginRecipe("bridge-egg-craft") {
    override fun build(plugin: JavaPlugin): Recipe {
        val recipe = ShapedRecipe(namespacedKey(plugin), BridgeEggItem(plugin).create())
        recipe.shape(
            "CCC",
            "CEC",
            "CCC"
        )

        recipe.setIngredient('C', vanillaChoice(Material.COBBLESTONE))
        recipe.setIngredient('E', vanillaChoice(Material.EGG))

        return recipe
    }
}