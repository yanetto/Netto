package com.yanetto.netto.ui.recipeScreen

import com.yanetto.netto.model.Ingredient
import com.yanetto.netto.model.IngredientInRecipe
import com.yanetto.netto.model.NutritionalOption
import com.yanetto.netto.model.Recipe

val recipe = Recipe(
    "Sticky Red Wine Shallots With Oregano Polenta",
    "Description",
    3,
    listOf(
        IngredientInRecipe(
            Ingredient("Red wine", "Shato Taman", 1000f, 30f, 0f, 0f, 7.5f),
            100f
        ),
        IngredientInRecipe(
            Ingredient("Coarse corn", "Super Corn", 300f, 30f, 20f, 5f, 7.5f),
            37.5f
        ),
        IngredientInRecipe(Ingredient("Shallots", "Best lots", 100f, 30f, 1f, 1f, 7.5f), 150f)
    )
)
data class RecipeUiState (
    val currentRecipe: Recipe = recipe,
    val updatedServingsCount: Int = recipe.servingsCount,
    val updatedIngredients: List<IngredientInRecipe> = recipe.ingredientList,
    val updatedWeight: Float = recipe.totalWeight,
    val newWeight: Float = recipe.totalWeight,
    val selectedNutritionalOption: NutritionalOption = NutritionalOption.TOTAL
)

