package com.yanetto.netto.data

import com.yanetto.netto.model.Ingredient
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun getAllItemsStream(): Flow<List<Ingredient>>
    fun getItemStream(id: Int): Flow<Ingredient?>
    suspend fun insertItem(ingredient: Ingredient)
    suspend fun deleteItem(ingredient: Ingredient)
    suspend fun updateItem(ingredient: Ingredient)
}