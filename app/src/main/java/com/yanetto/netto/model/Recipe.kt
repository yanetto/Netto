package com.yanetto.netto.model

data class Recipe(
    var name: String,
    var description: String,
    var servingsCount: Int,
    var ingredientList: List<IngredientInRecipe>,
    var energy: Float,
    var protein: Float,
    var fat: Float,
    var carbohydrates: Float,
    var totalWeight: Float,
    var totalPrice: Float
)
