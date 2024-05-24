package com.yanetto.netto.ui.recipeScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.yanetto.netto.NettoApplication
import com.yanetto.netto.data.RecipeRepository
import com.yanetto.netto.model.Ingredient
import com.yanetto.netto.model.NutritionalOption
import com.yanetto.netto.ui.editRecipeScreen.toRecipeDetails
import kotlinx.coroutines.launch
import java.math.RoundingMode

class RecipeViewModel(
    savedStateHandle: SavedStateHandle,
    private val recipeRepository: RecipeRepository
): ViewModel() {
    var uiState by mutableStateOf(RecipeUiState())

    private val recipeId: Int = savedStateHandle[RecipeDetailsDestination.recipeIdArg] ?: -1

    init {
        if (recipeId != -1){
            viewModelScope.launch {
                val recipeDetails = recipeRepository.getRecipe(recipeId).toRecipeDetails()

                uiState = uiState.copy(
                    recipeDetails = recipeDetails,
                    updatedServingsCount = recipeDetails.servingsCount.toFloat(),
                    selectedNutritionalOption = NutritionalOption.TOTAL
                )

                recipeRepository.getIngredientsWithWeights(recipeId)
                    .collect { ingredientsWithWeights ->
                        uiState = uiState.copy(listOfIngredients = ingredientsWithWeights)
                    }

                uiState = uiState.copy(
                    updatedIngredients = uiState.listOfIngredients.map { it.ingredient },
                    updatedWeight = uiState.listOfIngredients.map { it.ingredientWeight }.sum(),
                    newWeight = uiState.listOfIngredients.map { it.ingredientWeight }.sum()
                )
            }
        }
    }

    var totalPrice = uiState.listOfIngredients.sumOf { it.ingredient.price/it.ingredient.weight * it.ingredientWeight.toDouble()}.toFloat()
    var totalWeight = uiState.listOfIngredients.sumOf {it.ingredientWeight.toDouble()}.toFloat()
    var energy = uiState.listOfIngredients.sumOf {it.ingredient.energy.toDouble()}.toFloat()
    var protein = uiState.listOfIngredients.sumOf {it.ingredient.protein.toDouble()}.toFloat()
    var fat = uiState.listOfIngredients.sumOf {it.ingredient.fat.toDouble()}.toFloat()
    var carbohydrates = uiState.listOfIngredients.sumOf {it.ingredient.carbohydrates.toDouble()}.toFloat()

    fun getIngredientWeight(index: Int, newTotalWeight: Float): Float{
        val ingredient = uiState.listOfIngredients[index]
        return ingredient.ingredientWeight * newTotalWeight / totalWeight
    }

    fun changeServingCount(increment: Boolean){
        val servingsCount = uiState.updatedServingsCount

        val newServingCount: Float
        if (increment){
            newServingCount = servingsCount.toInt() + 1f
            uiState = uiState.copy(updatedServingsCount = newServingCount)
        }
        else {
            newServingCount = (if (servingsCount == servingsCount.toInt().toFloat()) servingsCount.toInt() - 1f else servingsCount.toInt().toFloat())
            uiState = uiState.copy(updatedServingsCount = newServingCount)
        }

        changeServingsCount(newServingCount)
    }

    private fun changeServingsCount(newServingCount: Float){
        val recipeServingCount = uiState.recipeDetails.servingsCount
        val totalWeight = totalWeight
        uiState = uiState.copy(updatedWeight = (totalWeight * newServingCount / recipeServingCount.toFloat()).toBigDecimal().setScale(1, RoundingMode.UP).toFloat())
        changeNutritionalOption(uiState.selectedNutritionalOption)
    }

   fun changeNutritionalOption(option: NutritionalOption){
        when(option){
            NutritionalOption.SERVING -> {
                uiState = uiState.copy(newWeight = (totalWeight / uiState.recipeDetails.servingsCount.toFloat())
                        .toBigDecimal().setScale(1, RoundingMode.UP).toFloat())
            }
            NutritionalOption.HUNDRED_GRAMS -> {
                uiState = uiState.copy(newWeight = 100f)
            }
            NutritionalOption.TOTAL -> {
                uiState = uiState.copy(newWeight = uiState.updatedWeight)
            }
        }
       uiState = uiState.copy(selectedNutritionalOption = option)
    }

    fun onChangeIngredientWeight(ingredientRecipe: Ingredient, newIngredientWeight: Float){
        val part = ingredientRecipe.weight / totalWeight
        val updatedWeight = newIngredientWeight / part
        uiState = uiState.copy(updatedWeight = updatedWeight, updatedServingsCount = updatedWeight * uiState.recipeDetails.servingsCount.toFloat() / totalWeight)
        changeNutritionalOption(uiState.selectedNutritionalOption)
    }

    fun update(){
        totalPrice = uiState.listOfIngredients.sumOf { it.ingredient.price/it.ingredient.weight * it.ingredientWeight.toDouble()}.toFloat()
        totalWeight = uiState.listOfIngredients.sumOf {it.ingredientWeight.toDouble()}.toFloat()
        energy = uiState.listOfIngredients.sumOf {it.ingredient.energy.toDouble()}.toFloat()
        protein = uiState.listOfIngredients.sumOf {it.ingredient.protein.toDouble()}.toFloat()
        fat = uiState.listOfIngredients.sumOf {it.ingredient.fat.toDouble()}.toFloat()
        carbohydrates = uiState.listOfIngredients.sumOf {it.ingredient.carbohydrates.toDouble()}.toFloat()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                val savedStateHandle = extras.createSavedStateHandle()

                return RecipeViewModel(
                    recipeRepository = (application as NettoApplication).container.recipeRepository,
                    savedStateHandle = savedStateHandle
                ) as T
            }
        }
    }
}