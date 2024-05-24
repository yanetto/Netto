package com.yanetto.netto.ui.recipeScreen

import com.yanetto.netto.model.Ingredient
import com.yanetto.netto.model.IngredientWithWeight
import com.yanetto.netto.model.NutritionalOption
import com.yanetto.netto.ui.editRecipeScreen.RecipeDetails

data class RecipeUiState (
    val recipeDetails: RecipeDetails = RecipeDetails(),
    val listOfIngredients: List<IngredientWithWeight> = listOf(),
    val updatedServingsCount: Float = recipeDetails.servingsCount.toFloat(),
    val updatedIngredients: List<Ingredient> = listOfIngredients.map { it.ingredient },
    val updatedWeight: Float = listOfIngredients.map { it.ingredientWeight }.sum(),
    val newWeight: Float = updatedWeight,
    val selectedNutritionalOption: NutritionalOption = NutritionalOption.TOTAL
)

