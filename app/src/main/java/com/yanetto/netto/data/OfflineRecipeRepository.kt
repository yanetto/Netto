package com.yanetto.netto.data

import com.yanetto.netto.model.Ingredient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class OfflineRecipeRepository(private val ingredientDao: IngredientDao) : RecipeRepository {
    override fun getAllIngredientsStream(): Flow<List<Ingredient>> = ingredientDao.getAllIngredients()

    override fun getIngredientStream(id: Int): Flow<Ingredient?> = ingredientDao.getIngredient(id)

    override suspend fun getIngredient(id: Int): Ingredient = ingredientDao.getIngredient(id).first()

    override suspend fun insertIngredient(ingredient: Ingredient) = ingredientDao.insert(ingredient)

    override suspend fun deleteIngredient(ingredient: Ingredient) = ingredientDao.delete(ingredient)

    override suspend fun updateIngredient(ingredient: Ingredient) = ingredientDao.update(ingredient)
}