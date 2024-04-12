package com.yanetto.netto.model

data class Ingredient(
    var name: String,
    var manufacturer: String,
    var pricePerKg:Float,
    var energy: Float,
    var protein: Float,
    var fat: Float,
    var carbohydrates: Float
)
