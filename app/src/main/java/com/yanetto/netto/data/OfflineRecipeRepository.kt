package com.yanetto.netto.data

import com.yanetto.netto.model.Ingredient
import kotlinx.coroutines.flow.Flow

class OfflineRecipeRepository(private val ingredientDao: IngredientDao) : RecipeRepository {
    override fun getAllItemsStream(): Flow<List<Ingredient>> = ingredientDao.getAllItems()

    override fun getItemStream(id: Int): Flow<Ingredient?> = ingredientDao.getItem(id)

    override suspend fun insertItem(item: Ingredient) = ingredientDao.insert(item)

    override suspend fun deleteItem(item: Ingredient) = ingredientDao.delete(item)

    override suspend fun updateItem(item: Ingredient) = ingredientDao.update(item)
}