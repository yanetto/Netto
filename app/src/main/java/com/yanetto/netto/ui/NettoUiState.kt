package com.yanetto.netto.ui

import com.yanetto.netto.model.Ingredient
import com.yanetto.netto.model.Recipe

data class NettoUiState (
    val currentRecipe: Recipe = Recipe(
        "Sticky Red Wine Shallots With Oregano Polenta",
        "Description",
        3,
        listOf(
            Ingredient("Red wine", "Shato Taman", 93.5f, 30f, 0f, 0f, 7.5f),
            Ingredient("Red wine", "Shato Taman", 93.5f, 30f, 0f, 0f, 7.5f),
            Ingredient("Red wine", "Shato Taman", 93.5f, 30f, 0f, 0f, 7.5f)
        ),
        204f,
        15f,
        8f,
        30f,
        500f,
        350f
    )
)