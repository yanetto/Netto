package com.yanetto.netto.data

import android.content.Context

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val recipeRepository: RecipeRepository
}
/**
 * [AppContainer] implementation that provides instance of [OfflineRecipeRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [RecipeRepository]
     */
    override val recipeRepository: RecipeRepository by lazy {
        OfflineRecipeRepository(RecipeDatabase.getDatabase(context).ingredientDao())
    }
}