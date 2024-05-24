package com.yanetto.netto.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class RecipeWithIngredients (
    @Embedded
    val recipe: Recipe,
    @Relation(
        parentColumn = "id",
        entity = Ingredient::class,
        entityColumn = "id",
        associateBy = Junction(
            value = IngredientRecipe::class,
            parentColumn = "recipeId",
            entityColumn = "ingredientId"
        )
    )
    val ingredients: List<Ingredient>
)


data class IngredientWithWeight(
    @Embedded val ingredient: Ingredient,
    val ingredientWeight: Float
)
