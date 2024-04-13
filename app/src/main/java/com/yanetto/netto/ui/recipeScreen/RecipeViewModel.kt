package com.yanetto.netto.ui.recipeScreen

import androidx.lifecycle.ViewModel
import com.yanetto.netto.model.IngredientInRecipe
import com.yanetto.netto.model.NutritionalOption
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.math.RoundingMode

class RecipeViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(RecipeUiState())
    val uiState: StateFlow<RecipeUiState> = _uiState.asStateFlow()

    fun changeServingCount(increment: Boolean){
        val servingsCount = _uiState.value.updatedServingsCount

        val newServingCount: Int
        if (increment){
            newServingCount = (servingsCount + 1)
            _uiState.update {
                currentState -> currentState.copy(updatedServingsCount = newServingCount)
            }
        }
        else {
            newServingCount = (servingsCount - 1)
            _uiState.update {
                    currentState -> currentState.copy(updatedServingsCount = newServingCount)
            }
        }

        changeServingsCount(newServingCount)
    }

    private fun changeServingsCount(newServingCount: Int){
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
        val part = _uiState.value.currentRecipe.getIngredientPartOfTotalWeight(ingredientInRecipe)
        _uiState.update {
            currentState -> currentState.copy(updatedWeight = newIngredientWeight / part)
        }
        changeNutritionalOption(_uiState.value.selectedNutritionalOption)
    }
}