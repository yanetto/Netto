package com.yanetto.netto.data

import com.yanetto.netto.model.Ingredient
import com.yanetto.netto.model.IngredientRecipe
import com.yanetto.netto.model.IngredientWithWeight
import com.yanetto.netto.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun getAllIngredientsStream(): Flow<List<Ingredient>>

    suspend fun getIngredient(id: Int): Ingredient
    suspend fun insertIngredient(ingredient: Ingredient)
    suspend fun deleteIngredient(ingredient: Ingredient)
    suspend fun updateIngredient(ingredient: Ingredient)



    fun getAllRecipesStream(): Flow<List<Recipe>>

    suspend fun getRecipe(id: Int): Recipe
    suspend fun insertRecipe(recipe: Recipe)
    suspend fun deleteRecipe(recipe: Recipe)
    suspend fun updateRecipe(recipe: Recipe)



    suspend fun insertRecipeWithIngredients(join: IngredientRecipe)
    suspend fun deleteIngredientsFromRecipe(recipeId: Int)
    suspend fun getIngredientsWithWeights(recipeId: Int): Flow<List<IngredientWithWeight>>


}