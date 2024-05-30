package com.yanetto.netto.ui.recipeScreen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.yanetto.netto.NettoApplication
import com.yanetto.netto.data.RecipeRepository
import com.yanetto.netto.model.IngredientWithWeight
import com.yanetto.netto.model.NutritionalOption
import com.yanetto.netto.ui.editRecipeScreen.toRecipeDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.math.RoundingMode

class RecipeViewModel(
    savedStateHandle: SavedStateHandle,
    private val recipeRepository: RecipeRepository
): ViewModel() {
    private val _recipeUiState = MutableStateFlow(RecipeUiState())
    val recipeUiState: StateFlow<RecipeUiState> = _recipeUiState

    private val recipeId: Int = savedStateHandle[RecipeDetailsDestination.recipeIdArg] ?: -1

    init {
            viewModelScope.launch {
                val recipeDetails = recipeRepository.getRecipe(recipeId).toRecipeDetails()

                _recipeUiState.value = _recipeUiState.value.copy(
                    recipeDetails = recipeDetails,
                    updatedServingsCount = recipeDetails.servingsCount.toFloat()
                )

                recipeRepository.getIngredientsWithWeights(recipeId)
                    .collect { ingredientsWithWeights ->
                        _recipeUiState.value = _recipeUiState.value.copy(
                            listOfIngredients = ingredientsWithWeights,
                            updatedIngredients =  ingredientsWithWeights.map { it.ingredient },
                            updatedWeight = ingredientsWithWeights.map { it.ingredientWeight }.sum(),
                            newWeight = ingredientsWithWeights.map { it.ingredientWeight }.sum(),
                            selectedNutritionalOption = NutritionalOption.TOTAL,
                            totalPrice = ingredientsWithWeights.sumOf { it.ingredient.price/it.ingredient.weight * it.ingredientWeight.toDouble()}.toFloat(),
                            totalWeight = ingredientsWithWeights.sumOf {it.ingredientWeight.toDouble()}.toFloat(),
                            energy = (ingredientsWithWeights.sumOf {it.ingredient.energy.toDouble() * it.ingredientWeight} / ingredientsWithWeights.map { it.ingredientWeight }.sum()).toFloat(),
                            protein = (ingredientsWithWeights.sumOf {it.ingredient.protein.toDouble() * it.ingredientWeight}  / ingredientsWithWeights.map { it.ingredientWeight }.sum()).toFloat(),
                            fat = (ingredientsWithWeights.sumOf {it.ingredient.fat.toDouble() * it.ingredientWeight} / ingredientsWithWeights.map { it.ingredientWeight }.sum()).toFloat(),
                            carbohydrates = ((ingredientsWithWeights.sumOf {it.ingredient.carbohydrates.toDouble() * it.ingredientWeight} / ingredientsWithWeights.map { it.ingredientWeight }.sum()).toFloat())
                        )
                    }
            }
    }



    fun getIngredientWeight(index: Int, newTotalWeight: Float): Float{
        Log.d("ingredientsWithWeights", "${_recipeUiState.value.listOfIngredients}")
        val ingredient = _recipeUiState.value.listOfIngredients[index]
        return ingredient.ingredientWeight * newTotalWeight / _recipeUiState.value.totalWeight
    }

    fun changeServingCount(increment: Boolean){
        val servingsCount = _recipeUiState.value.updatedServingsCount

        val newServingCount: Float
        if (increment){
            newServingCount = servingsCount.toInt() + 1f
            _recipeUiState.value = _recipeUiState.value.copy(updatedServingsCount = newServingCount)
        }
        else {
            newServingCount = (if (servingsCount == servingsCount.toInt().toFloat()) servingsCount.toInt() - 1f else servingsCount.toInt().toFloat())
            _recipeUiState.value = _recipeUiState.value.copy(updatedServingsCount = newServingCount)
        }

        changeServingsCount(newServingCount)
    }

    private fun changeServingsCount(newServingCount: Float){
        val recipeServingCount = _recipeUiState.value.recipeDetails.servingsCount
        val totalWeight = _recipeUiState.value.totalWeight
        _recipeUiState.value = _recipeUiState.value.copy(updatedWeight = (totalWeight * newServingCount / recipeServingCount.toFloat()).toBigDecimal().setScale(1, RoundingMode.UP).toFloat())
        changeNutritionalOption(_recipeUiState.value.selectedNutritionalOption)
    }

   fun changeNutritionalOption(option: NutritionalOption){
        when(option){
            NutritionalOption.SERVING -> {
                _recipeUiState.value = _recipeUiState.value.copy(newWeight = (_recipeUiState.value.totalWeight / _recipeUiState.value.recipeDetails.servingsCount.toFloat())
                        .toBigDecimal().setScale(1, RoundingMode.UP).toFloat())
            }
            NutritionalOption.HUNDRED_GRAMS -> {
                _recipeUiState.value = _recipeUiState.value.copy(newWeight = 100f)
            }
            NutritionalOption.TOTAL -> {
                _recipeUiState.value = _recipeUiState.value.copy(newWeight = _recipeUiState.value.updatedWeight)
            }
        }
       _recipeUiState.value = _recipeUiState.value.copy(selectedNutritionalOption = option)
    }

    fun onChangeIngredientWeight(ingredient: IngredientWithWeight, newIngredientWeight: Float){
        val part = ingredient.ingredientWeight / _recipeUiState.value.totalWeight
        val updatedWeight = newIngredientWeight / part
        _recipeUiState.value = _recipeUiState.value.copy(updatedWeight = updatedWeight, updatedServingsCount = updatedWeight * _recipeUiState.value.recipeDetails.servingsCount.toFloat() / _recipeUiState.value.totalWeight)
        changeNutritionalOption(_recipeUiState.value.selectedNutritionalOption)
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