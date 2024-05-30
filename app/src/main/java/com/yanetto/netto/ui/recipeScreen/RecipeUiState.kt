package com.yanetto.netto.ui.recipeScreen

import com.yanetto.netto.model.Ingredient
import com.yanetto.netto.model.IngredientWithWeight
import com.yanetto.netto.model.NutritionalOption
import com.yanetto.netto.ui.editRecipeScreen.RecipeDetails

data class RecipeUiState (
    val recipeDetails: RecipeDetails = RecipeDetails(),
    val listOfIngredients: List<IngredientWithWeight> = listOf(),
    val updatedServingsCount: Float = 0f,
    val updatedIngredients: List<Ingredient> = listOf(),
    val updatedWeight: Float = 0f,
    val newWeight: Float = 0f,
    val selectedNutritionalOption: NutritionalOption = NutritionalOption.TOTAL,
    val totalPrice: Float = 0f,
    val totalWeight: Float = 0f,
    val energy: Float = 0f,
    val protein: Float = 0f,
    val fat: Float = 0f,
    val carbohydrates: Float = 0f
)

