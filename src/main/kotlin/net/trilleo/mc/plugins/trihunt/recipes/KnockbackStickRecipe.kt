package net.trilleo.mc.plugins.trihunt.recipes

import net.trilleo.mc.plugins.trihunt.items.utilityItems.KnockbackStickItem
import net.trilleo.mc.plugins.trihunt.registration.PluginRecipe
import org.bukkit.Material
import org.bukkit.inventory.Recipe
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.plugin.java.JavaPlugin

class KnockbackStickRecipe : PluginRecipe("knockback-stick-craft") {
    override fun build(plugin: JavaPlugin): Recipe {
        val recipe = ShapedRecipe(namespacedKey(plugin), KnockbackStickItem(plugin).create())
        recipe.shape(
            " D ",
            "DSD",
            " D "
        )

        recipe.setIngredient('D', vanillaChoice(Material.DIAMOND))
        recipe.setIngredient('S', vanillaChoice(Material.STICK))

        return recipe
    }
}