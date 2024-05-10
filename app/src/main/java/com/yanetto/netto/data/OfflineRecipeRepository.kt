package com.yanetto.netto.data

import com.yanetto.netto.model.Ingredient
import kotlinx.coroutines.flow.Flow

class OfflineRecipeRepository(private val ingredientDao: IngredientDao) : RecipeRepository {
    override fun getAllIngredientsStream(): Flow<List<Ingredient>> = ingredientDao.getAllItems()

    override fun getItemStream(id: Int): Flow<Ingredient?> = ingredientDao.getItem(id)

    override suspend fun insertItem(ingredient: Ingredient) = ingredientDao.insert(ingredient)

    override suspend fun deleteItem(ingredient: Ingredient) = ingredientDao.delete(ingredient)

    override suspend fun updateItem(ingredient: Ingredient) = ingredientDao.update(ingredient)
}