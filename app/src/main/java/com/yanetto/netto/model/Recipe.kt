package com.yanetto.netto.model

data class Recipe(
    val name: String,
    val description: String,
    val servingsCount: Int,
    val ingredientList: List<Ingredient>,
    val energy: Float,
    val protein: Float,
    val fat: Float,
    val carbohydrates: Float,
    val totalWeight: Float,
    val totalPrice: Float
)
