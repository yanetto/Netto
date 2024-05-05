package com.yanetto.netto.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yanetto.netto.model.Ingredient

@Database(entities = [Ingredient::class], version = 1, exportSchema = false)
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