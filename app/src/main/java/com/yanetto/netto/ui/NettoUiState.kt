package com.yanetto.netto.ui

import com.yanetto.netto.model.Ingredient
import com.yanetto.netto.model.IngredientInRecipe
import com.yanetto.netto.model.Recipe

data class NettoUiState (
    val currentRecipePosition: Int = 0,
    val recipes: List<Recipe> = listOf(
        Recipe(
            "Sticky Red Wine Shallots With Oregano Polenta",
            "Description",
            3,
            listOf(
                IngredientInRecipe(Ingredient("Red wine", "Shato Taman", 93.5f, 30f, 0f, 0f, 7.5f), 100f),
                IngredientInRecipe(Ingredient("Red wine", "Shato Taman", 93.5f, 30f, 0f, 0f, 7.5f), 100f),
                IngredientInRecipe(Ingredient("Red wine", "Shato Taman", 93.5f, 30f, 0f, 0f, 7.5f), 100f)
            ),
            204f,
            15f,
            8f,
            30f,
            300f,
            350f
        ),
        Recipe(
            "Sticky Red Wine Shallots With Oregano Polenta",
            "Description",
            3,
            listOf(
                IngredientInRecipe(Ingredient("Red wine", "Shato Taman", 93.5f, 30f, 0f, 0f, 7.5f), 100f),
                IngredientInRecipe(Ingredient("Red wine", "Shato Taman", 93.5f, 30f, 0f, 0f, 7.5f), 100f),
                IngredientInRecipe(Ingredient("Red wine", "Shato Taman", 93.5f, 30f, 0f, 0f, 7.5f), 100f)
            ),
            204f,
            15f,
            8f,
            30f,
            300f,
            350f
        )
    )
)