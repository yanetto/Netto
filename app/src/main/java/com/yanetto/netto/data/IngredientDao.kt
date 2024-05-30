package com.yanetto.netto.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.yanetto.netto.model.Ingredient
import com.yanetto.netto.model.IngredientRecipe
import com.yanetto.netto.model.IngredientWithWeight
import com.yanetto.netto.model.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIngredient(ingredient: Ingredient)

    @Update
    suspend fun updateIngredient(ingredient: Ingredient)

    @Delete
    suspend fun deleteIngredient(ingredient: Ingredient)

    @Query("SELECT * from ingredients WHERE id = :id")
    fun getIngredient(id: Int): Flow<Ingredient>

    @Query("SELECT * from ingredients ORDER BY name ASC")
    fun getAllIngredients(): Flow<List<Ingredient>>




    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRecipeWithIngredients(join: IngredientRecipe)

    @Query("DELETE FROM ingredientRecipe WHERE recipeId = :recipeId")
    fun deleteIngredientsFromRecipe(recipeId: Int)




    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecipe(recipe: Recipe)

    @Update
    suspend fun updateRecipe(recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

    @Query("SELECT * from recipes WHERE id = :id")
    fun getRecipe(id: Int): Flow<Recipe>

    @Query("SELECT * from recipes ORDER BY name ASC")
    fun getAllRecipes(): Flow<List<Recipe>>

    @Transaction
    @Query("""
        SELECT 
            ingredients.id, 
            ingredients.name, 
            ingredients.manufacturer, 
            ingredients.price, 
            ingredients.weight, 
            ingredients.energy, 
            ingredients.protein, 
            ingredients.fat, 
            ingredients.carbohydrates, 
            ingredientRecipe.weight AS ingredientWeight
        FROM 
            ingredients
        INNER JOIN 
            ingredientRecipe 
        ON 
            ingredients.id = ingredientRecipe.ingredientId 
        WHERE 
            ingredientRecipe.recipeId = :recipeId
    """)
    fun getIngredientsWithWeights(recipeId: Int): Flow<List<IngredientWithWeight>>

}