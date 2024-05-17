package com.yanetto.netto.data

import com.yanetto.netto.model.Ingredient
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun getAllIngredientsStream(): Flow<List<Ingredient>>
    fun getIngredientStream(id: Int): Flow<Ingredient?>

    suspend fun getIngredient(id: Int): Ingredient
    suspend fun insertIngredient(ingredient: Ingredient)
    suspend fun deleteIngredient(ingredient: Ingredient)
    suspend fun updateIngredient(ingredient: Ingredient)
}