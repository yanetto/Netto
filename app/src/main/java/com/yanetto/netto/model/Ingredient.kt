package com.yanetto.netto.model

data class Ingredient(
    val name: String,
    val manufacturer: String,
    val pricePerKg: Float,
    val energy: Float,
    val protein: Float,
    val fat: Float,
    val carbohydrates: Float
)
