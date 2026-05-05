package net.trilleo.mc.plugins.trihunt.recipes

import net.trilleo.mc.plugins.trihunt.items.utilityItems.ShootableFireballItem
import net.trilleo.mc.plugins.trihunt.registration.PluginRecipe
import org.bukkit.Material
import org.bukkit.inventory.Recipe
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.plugin.java.JavaPlugin

class ShootableFireballRecipe : PluginRecipe("shootable-fireball-craft") {
    override fun build(plugin: JavaPlugin): Recipe {
        val recipe = ShapedRecipe(namespacedKey(plugin), ShootableFireballItem(plugin).create())
        recipe.shape(
            " G ",
            "GFG",
            " G "
        )

        recipe.setIngredient('G', Material.GUNPOWDER)
        recipe.setIngredient('F', Material.FIRE_CHARGE)

        return recipe
    }
}