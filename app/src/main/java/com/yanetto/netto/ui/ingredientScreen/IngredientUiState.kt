package com.yanetto.netto.ui.ingredientScreen

import com.yanetto.netto.model.Ingredient

data class IngredientUiState (
    val ingredientDetails: IngredientDetails = IngredientDetails(),
    val isEntryValid: Boolean = false
)

data class IngredientDetails(
    var id: Int = 0,
    var name: String = "",
    var manufacturer: String = "",
    var weight: String = "",
    var price: String = "",
    var energy: String = "",
    var protein: String = "",
    var fat: String = "",
    var carbohydrates: String = ""
)

fun IngredientDetails.toIngredient(): Ingredient = Ingredient(
    id = id,
    name = name,
    manufacturer = manufacturer,
    weight = weight.toFloatOrNull() ?: 0f,
    price = price.toFloatOrNull() ?: 0f,
    energy = energy.toFloatOrNull() ?: 0f,
    protein = protein.toFloatOrNull() ?: 0f,
    fat = fat.toFloatOrNull() ?: 0f,
    carbohydrates = carbohydrates.toFloatOrNull() ?: 0f
)

fun Ingredient.toItemUiState(isEntryValid: Boolean = false): IngredientUiState = IngredientUiState(
    ingredientDetails = this.toIngredientDetails(),
    isEntryValid = isEntryValid
)

fun Ingredient.toIngredientDetails() : IngredientDetails = IngredientDetails(
    id = id,
    name = name,
    manufacturer = manufacturer,
    weight = weight.toString(),
    price = price.toString(),
    energy = energy.toString(),
    protein = protein.toString(),
    fat = fat.toString(),
    carbohydrates = carbohydrates.toString()
)