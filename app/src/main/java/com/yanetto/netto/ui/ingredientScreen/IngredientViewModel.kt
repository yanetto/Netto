package com.yanetto.netto.ui.ingredientScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.yanetto.netto.NettoApplication
import com.yanetto.netto.data.RecipeRepository

class IngredientViewModel(
    private val recipeRepository: RecipeRepository
) : ViewModel() {
    var ingredientUiState by mutableStateOf(IngredientUiState())
        private set

    fun updateUiState(ingredientDetails: IngredientDetails) {
        ingredientUiState =
            IngredientUiState(ingredientDetails = ingredientDetails, isEntryValid = validateInput(ingredientDetails))
    }

    fun updateEnergy(energyValue: String){
        ingredientUiState =
            IngredientUiState(ingredientDetails = ingredientUiState.ingredientDetails.copy(energy = energyValue), isEntryValid = validateInput(ingredientUiState.ingredientDetails.copy(energy = energyValue)))
    }

    fun updateProtein(energyValue: String){
        ingredientUiState =
            IngredientUiState(ingredientDetails = ingredientUiState.ingredientDetails.copy(protein = energyValue), isEntryValid = validateInput(ingredientUiState.ingredientDetails.copy(protein = energyValue)))
    }

    fun updateFat(energyValue: String){
        ingredientUiState =
            IngredientUiState(ingredientDetails = ingredientUiState.ingredientDetails.copy(fat = energyValue), isEntryValid = validateInput(ingredientUiState.ingredientDetails.copy(fat = energyValue)))
    }

    fun updateCarbohydrates(energyValue: String){
        ingredientUiState =
            IngredientUiState(ingredientDetails = ingredientUiState.ingredientDetails.copy(carbohydrates = energyValue), isEntryValid = validateInput(ingredientUiState.ingredientDetails.copy(carbohydrates = energyValue)))
    }

    private fun validateInput(uiState: IngredientDetails = ingredientUiState.ingredientDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && manufacturer.isNotBlank() && price.isNotBlank() && weight.isNotBlank() && energy.isNotBlank() && protein.isNotBlank() && fat.isNotBlank() && carbohydrates.isNotBlank()
        }
    }

    suspend fun saveItem() {
        if (validateInput()) {
            recipeRepository.insertItem(ingredientUiState.ingredientDetails.toIngredient())
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as NettoApplication)
                val recipeRepository = application.container.recipeRepository
                IngredientViewModel(recipeRepository= recipeRepository)
            }
        }
    }
}