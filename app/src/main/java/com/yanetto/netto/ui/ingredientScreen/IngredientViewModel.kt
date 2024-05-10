package com.yanetto.netto.ui.ingredientScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.yanetto.netto.NettoApplication
import com.yanetto.netto.data.RecipeRepository
import com.yanetto.netto.model.Ingredient
import com.yanetto.netto.model.Recipe
import kotlinx.coroutines.flow.update

class IngredientViewModel(
    private val recipeRepository: RecipeRepository
) : ViewModel() {
    var ingredientUiState by mutableStateOf(IngredientUiState())
        private set

    fun updateUiState(ingredientDetails: IngredientDetails) {
        ingredientUiState =
            IngredientUiState(ingredientDetails = ingredientDetails, isEntryValid = validateInput(ingredientDetails))
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