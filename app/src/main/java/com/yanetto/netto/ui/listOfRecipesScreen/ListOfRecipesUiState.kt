package com.yanetto.netto.ui.listOfRecipesScreen

import com.yanetto.netto.model.Recipe

data class ListOfRecipesUiState(
    val recipeList: List<Recipe> = listOf()
)