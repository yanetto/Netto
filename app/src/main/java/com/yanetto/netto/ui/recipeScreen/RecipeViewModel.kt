package com.yanetto.netto.ui.recipeScreen

import androidx.lifecycle.ViewModel
import com.yanetto.netto.model.IngredientInRecipe
import com.yanetto.netto.model.NutritionalOption
import com.yanetto.netto.model.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.math.RoundingMode

class RecipeViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(RecipeUiState())
    val uiState: StateFlow<RecipeUiState> = _uiState.asStateFlow()


    fun setCurrentRecipe(recipe: Recipe){
        _uiState.update {
                currentState ->currentState.copy(currentRecipe = recipe)
        }
    }
    fun changeServingCount(increment: Boolean){
        val servingsCount = _uiState.value.updatedServingsCount

        val newServingCount: Float
        if (increment){
            newServingCount = servingsCount.toInt() + 1f
            _uiState.update {
                currentState -> currentState.copy(updatedServingsCount = newServingCount)
            }
        }
        else {
            newServingCount = (if (servingsCount == servingsCount.toInt().toFloat()) servingsCount.toInt() - 1f else servingsCount.toInt().toFloat())
            _uiState.update {
                    currentState -> currentState.copy(updatedServingsCount = newServingCount)
            }
        }

        changeServingsCount(newServingCount)
    }

    private fun changeServingsCount(newServingCount: Float){
        val recipeServingCount = _uiState.value.currentRecipe.servingsCount
        val totalWeight = _uiState.value.currentRecipe.totalWeight
        _uiState.update {
                currentState -> currentState.copy(updatedWeight = (totalWeight * newServingCount / recipeServingCount).toBigDecimal().setScale(1, RoundingMode.UP).toFloat())
        }
        changeNutritionalOption(_uiState.value.selectedNutritionalOption)
    }

   fun changeNutritionalOption(option: NutritionalOption){
        when(option){
            NutritionalOption.SERVING -> {
                _uiState.update {
                    currentState -> currentState.copy(newWeight = (_uiState.value.currentRecipe.totalWeight / _uiState.value.currentRecipe.servingsCount)
                        .toBigDecimal().setScale(1, RoundingMode.UP).toFloat())
                }
            }
            NutritionalOption.HUNDRED_GRAMS -> {
                _uiState.update {
                        currentState -> currentState.copy(newWeight = 100f)
                }
            }
            NutritionalOption.TOTAL -> {
                _uiState.update {
                        currentState -> currentState.copy(newWeight = _uiState.value.updatedWeight)
                }
            }
        }
       _uiState.update {
           currentState -> currentState.copy(selectedNutritionalOption = option)
       }
    }

    fun onChangeIngredientWeight(ingredientInRecipe: IngredientInRecipe, newIngredientWeight: Float){
        val part = ingredientInRecipe.weight / _uiState.value.currentRecipe.totalWeight
        val updatedWeight = newIngredientWeight / part
        _uiState.update {
            currentState -> currentState.copy(updatedWeight = updatedWeight, updatedServingsCount = updatedWeight * _uiState.value.currentRecipe.servingsCount / _uiState.value.currentRecipe.totalWeight)
        }
        changeNutritionalOption(_uiState.value.selectedNutritionalOption)
    }
}