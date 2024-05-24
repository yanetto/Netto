package com.yanetto.netto.ui.editRecipeScreen

import com.yanetto.netto.model.IngredientWithWeight
import com.yanetto.netto.model.Recipe

data class EditRecipeUiState (
    val recipeDetails: RecipeDetails = RecipeDetails(),
    val isEntryValid: Boolean = false,
    val listOfIngredients: List<IngredientWithWeight> = listOf()
)

data class RecipeDetails(
    var id: Int = 0,
    var name: String = "Recipe name",
    var description: String = "Description",
    var servingsCount: String = "1"
)

fun RecipeDetails.toRecipe(): Recipe = Recipe(
    id = id,
    name = name,
    description = description,
    servingsCount = servingsCount.toIntOrNull() ?: 1
)

fun Recipe.toEditRecipeUiState(isEntryValid: Boolean = false): EditRecipeUiState = EditRecipeUiState(
    recipeDetails = this.toRecipeDetails(),
    isEntryValid = isEntryValid
)

fun Recipe.toRecipeDetails(): RecipeDetails = RecipeDetails(
    id = id,
    name = name,
    description = description,
    servingsCount = servingsCount.toString()
)