package com.yanetto.netto.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.yanetto.netto.model.Ingredient
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(ingredient: Ingredient)

    @Update
    suspend fun update(ingredient: Ingredient)

    @Delete
    suspend fun delete(ingredient: Ingredient)

    @Query("SELECT * from ingredients WHERE id = :id")
    fun getIngredient(id: Int): Flow<Ingredient>

    @Query("SELECT * from ingredients ORDER BY name ASC")
    fun getAllIngredients(): Flow<List<Ingredient>>
}