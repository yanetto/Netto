package com.yanetto.netto.model

import androidx.room.Embedded

data class IngredientWithWeight(
    @Embedded val ingredient: Ingredient,
    val ingredientWeight: Float
)
