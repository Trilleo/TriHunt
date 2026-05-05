package net.trilleo.mc.plugins.trihunt.guis.infoMenus

import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.MiniMessage
import net.trilleo.mc.plugins.trihunt.registration.GUIManager
import net.trilleo.mc.plugins.trihunt.registration.PagedPluginGUI
import net.trilleo.mc.plugins.trihunt.registration.RecipeRegistrar
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.BlastingRecipe
import org.bukkit.inventory.CampfireRecipe
import org.bukkit.inventory.FurnaceRecipe
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.Recipe
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.ShapelessRecipe
import org.bukkit.inventory.SmithingTransformRecipe
import org.bukkit.inventory.SmokingRecipe
import org.bukkit.inventory.StonecuttingRecipe

/**
 * A paged GUI that lists every plugin recipe, sorted alphabetically by
 * result-item display name.  Each icon is the recipe's result item with
 * extra lore lines indicating the recipe type and a click hint.
 *
 * Clicking a recipe icon opens [RecipeDetailUI] for that recipe, preserving
 * the current page so the Back button in the detail view returns here.
 */
class RecipeBookUI : PagedPluginGUI(
    id = "recipe-book",
    title = Component.text("Recipe Book").color(NamedTextColor.GREEN).decorate(TextDecoration.BOLD),
    rows = 6
) {
    private companion object {
        val MM: MiniMessage = MiniMessage.miniMessage()
        val MM_TAG_PATTERN = Regex("<[^>]*>")
    }

    /** Returns all registered plugin recipes sorted alphabetically by result name. */
    private fun sortedRecipes(): List<Recipe> =
        RecipeRegistrar.getRegisteredKeys()
            .mapNotNull { key -> Bukkit.getRecipe(key) }
            .sortedBy { recipe ->
                val name = recipe.result.itemMeta?.displayName()
                if (name != null) MM.serialize(name).replace(MM_TAG_PATTERN, "").lowercase()
                else recipe.result.type.name.lowercase()
            }

    private fun recipeTypeName(recipe: Recipe): String = when (recipe) {
        is ShapedRecipe -> "Crafting Table"
        is ShapelessRecipe -> "Crafting Table"
        is FurnaceRecipe -> "Furnace"
        is BlastingRecipe -> "Blast Furnace"
        is SmokingRecipe -> "Smoker"
        is CampfireRecipe -> "Campfire"
        is StonecuttingRecipe -> "Stonecutter"
        is SmithingTransformRecipe -> "Smithing Table"
        else -> "Unknown"
    }

    override fun getItems(player: Player): List<ItemStack> =
        sortedRecipes().map { recipe ->
            val icon = recipe.result.clone().also { it.amount = 1 }
            val meta = icon.itemMeta ?: return@map icon
            val existingLore = meta.lore() ?: emptyList()
            val separator = MM.deserialize("<reset><i:false><dark_gray>──────────────")
            val typeLine = MM.deserialize("<reset><i:false><gray>Type: <yellow>${recipeTypeName(recipe)}")
            val hintLine = MM.deserialize("<reset><i:false><gray>Click to view recipe")
            val newLore = existingLore.toMutableList().also { lore ->
                if (lore.isNotEmpty()) lore.add(separator)
                lore.add(typeLine)
                lore.add(hintLine)
            }
            meta.lore(newLore)
            icon.itemMeta = meta
            icon
        }

    override fun onContentClick(event: InventoryClickEvent, page: Int) {
        val player = event.whoClicked as? Player ?: return
        val slot = event.rawSlot
        if (slot < 0) return
        val index = page * contentSlots + slot
        val recipes = sortedRecipes()
        if (index >= recipes.size) return

        player.playSound(Sound.sound(Key.key("minecraft:ui.button.click"), Sound.Source.UI, 1f, 1f))
        RecipeDetailUI.setPendingRecipe(player, recipes[index], page)
        GUIManager.open(player, "recipe-detail")
    }
}
