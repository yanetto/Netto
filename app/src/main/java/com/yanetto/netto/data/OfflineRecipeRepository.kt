package com.yanetto.netto.data

import com.yanetto.netto.model.Ingredient
import com.yanetto.netto.model.IngredientRecipe
import com.yanetto.netto.model.IngredientWithWeight
import com.yanetto.netto.model.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class OfflineRecipeRepository(private val ingredientDao: IngredientDao) : RecipeRepository {
    override fun getAllIngredientsStream(): Flow<List<Ingredient>> = ingredientDao.getAllIngredients()
    override suspend fun getIngredient(id: Int): Ingredient = ingredientDao.getIngredient(id).first()
    override suspend fun insertIngredient(ingredient: Ingredient) = ingredientDao.insertIngredient(ingredient)
    override suspend fun deleteIngredient(ingredient: Ingredient) = ingredientDao.deleteIngredient(ingredient)
    override suspend fun updateIngredient(ingredient: Ingredient) = ingredientDao.updateIngredient(ingredient)




    override fun getAllRecipesStream(): Flow<List<Recipe>> = ingredientDao.getAllRecipes()
    override suspend fun getRecipe(id: Int): Recipe = ingredientDao.getRecipe(id).first()
    override suspend fun insertRecipe(recipe: Recipe) = ingredientDao.insertRecipe(recipe)
    override suspend fun deleteRecipe(recipe: Recipe) = ingredientDao.deleteRecipe(recipe)
    override suspend fun updateRecipe(recipe: Recipe) = ingredientDao.updateRecipe(recipe)



    override suspend fun insertRecipeWithIngredients(join: IngredientRecipe) = ingredientDao.insertRecipeWithIngredients(join)
    override suspend fun deleteIngredientsFromRecipe(recipeId: Int) = ingredientDao.deleteIngredientsFromRecipe(recipeId)
    override suspend fun getIngredientsWithWeights(recipeId: Int): Flow<List<IngredientWithWeight>> = ingredientDao.getIngredientsWithWeights(recipeId)
}