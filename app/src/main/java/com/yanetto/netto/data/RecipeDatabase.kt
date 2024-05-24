package com.yanetto.netto.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yanetto.netto.model.Ingredient
import com.yanetto.netto.model.IngredientRecipe
import com.yanetto.netto.model.Recipe

@Database(entities = [Ingredient::class, Recipe::class, IngredientRecipe::class], version = 2, exportSchema = false)
abstract class RecipeDatabase:RoomDatabase() {
    abstract fun ingredientDao(): IngredientDao
    companion object {
        @Volatile
        private var Instance: RecipeDatabase? = null
        fun getDatabase(context: Context): RecipeDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, RecipeDatabase::class.java, "ingredient_database").fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}