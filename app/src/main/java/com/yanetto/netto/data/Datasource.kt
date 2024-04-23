package com.yanetto.netto.data

import com.yanetto.netto.model.Ingredient
import com.yanetto.netto.model.IngredientInRecipe
import com.yanetto.netto.model.Recipe

class Datasource {
    fun loadRecipes(): List<Recipe>{
        return listOf(
            Recipe("Mighty Green Pasta", "Veggie Pasta Dreams", 5, ingredientList = listOf(
                IngredientInRecipe(Ingredient("name", "manufacturer", 10f, 100f, 1f, 1f, 1f), 200f)
            )),
            Recipe("A Full English Salad", "Going AI Fresco", 2, ingredientList = listOf(
                IngredientInRecipe(Ingredient("name", "manufacturer", 10f, 100f, 1f, 1f, 1f), 200f)
            )),
            Recipe("Chorizo Carbonara", "Chorizo Stunners", 2, ingredientList = listOf(
                IngredientInRecipe(Ingredient("name", "manufacturer", 10f, 100f, 1f, 1f, 1f), 200f)
            )),
            Recipe("Mighty Green Pasta", "Veggie Pasta Dreams", 5, ingredientList = listOf(
                IngredientInRecipe(Ingredient("name", "manufacturer", 10f, 100f, 1f, 1f, 1f), 200f)
            )),
            Recipe("A Full English Salad", "Going AI Fresco", 2, ingredientList = listOf(
                IngredientInRecipe(Ingredient("name", "manufacturer", 10f, 100f, 1f, 1f, 1f), 200f)
            )),
            Recipe("Chorizo Carbonara", "Chorizo Stunners", 2, ingredientList = listOf(
                IngredientInRecipe(Ingredient("name", "manufacturer", 10f, 100f, 1f, 1f, 1f), 200f)
            )),
            Recipe("Mighty Green Pasta", "Veggie Pasta Dreams", 5, ingredientList = listOf(
                IngredientInRecipe(Ingredient("name", "manufacturer", 10f, 100f, 1f, 1f, 1f), 200f)
            )),
            Recipe("A Full English Salad", "Going AI Fresco", 2, ingredientList = listOf(
                IngredientInRecipe(Ingredient("name", "manufacturer", 10f, 100f, 1f, 1f, 1f), 200f)
            )),
            Recipe("Chorizo Carbonara", "Chorizo Stunners", 2, ingredientList = listOf(
                IngredientInRecipe(Ingredient("name", "manufacturer", 10f, 100f, 1f, 1f, 1f), 200f)
            )),
            Recipe("Mighty Green Pasta", "Veggie Pasta Dreams", 5, ingredientList = listOf(
                IngredientInRecipe(Ingredient("name", "manufacturer", 10f, 100f, 1f, 1f, 1f), 200f)
            )),
            Recipe("A Full English Salad", "Going AI Fresco", 2, ingredientList = listOf(
                IngredientInRecipe(Ingredient("name", "manufacturer", 10f, 100f, 1f, 1f, 1f), 200f)
            )),
            Recipe("Chorizo Carbonara", "Chorizo Stunners", 2, ingredientList = listOf(
                IngredientInRecipe(Ingredient("name", "manufacturer", 10f, 100f, 1f, 1f, 1f), 200f)
            )),
        )
    }
}