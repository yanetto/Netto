package com.yanetto.netto.model

import androidx.room.Entity

@Entity(tableName = "ingredientRecipe", primaryKeys = ["ingredientId", "recipeId"])
data class IngredientRecipe(
    var ingredientId: Int,
    var recipeId: Int,
    var weight: Float
)
