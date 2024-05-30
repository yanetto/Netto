package com.yanetto.netto.ui.ingredientScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.yanetto.netto.NettoApplication
import com.yanetto.netto.data.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class IngredientViewModel(
    private val recipeRepository: RecipeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _ingredientUiState = MutableStateFlow(IngredientUiState())
    val ingredientUiState: StateFlow<IngredientUiState> = _ingredientUiState

    private val ingredientId: Int = savedStateHandle[IngredientDetailsDestination.ingredientIdArg] ?: -1

    init {
        if (ingredientId != -1){
            viewModelScope.launch {
                val ingredient = recipeRepository.getIngredient(ingredientId)
                _ingredientUiState.value = _ingredientUiState.value.copy(ingredientDetails = ingredient.toIngredientDetails())

            }
        }
    }

    fun updateUiState(ingredientDetails: IngredientDetails) {
        _ingredientUiState.value =
            _ingredientUiState.value.copy(ingredientDetails = ingredientDetails, isEntryValid = validateInput(ingredientDetails))
    }

    fun updateEnergy(value: String){
        _ingredientUiState.value =
            _ingredientUiState.value.copy(ingredientDetails = _ingredientUiState.value.ingredientDetails.copy(energy = value), isEntryValid = validateInput(_ingredientUiState.value.ingredientDetails.copy(energy = value)))
    }

    fun updateProtein(value: String){
        _ingredientUiState.value =
            _ingredientUiState.value.copy(ingredientDetails = _ingredientUiState.value.ingredientDetails.copy(protein = value), isEntryValid = validateInput(_ingredientUiState.value.ingredientDetails.copy(protein = value)))
    }

    fun updateFat(value: String){
        _ingredientUiState.value =
            _ingredientUiState.value.copy(ingredientDetails = _ingredientUiState.value.ingredientDetails.copy(fat = value), isEntryValid = validateInput(_ingredientUiState.value.ingredientDetails.copy(fat = value)))
    }

    fun updateCarbohydrates(value: String){
        _ingredientUiState.value =
            _ingredientUiState.value.copy(ingredientDetails = _ingredientUiState.value.ingredientDetails.copy(carbohydrates = value), isEntryValid = validateInput(_ingredientUiState.value.ingredientDetails.copy(carbohydrates = value)))
    }

    fun updateWeight(value: String){
        _ingredientUiState.value =
            _ingredientUiState.value.copy(ingredientDetails = _ingredientUiState.value.ingredientDetails.copy(weight = value), isEntryValid = validateInput(_ingredientUiState.value.ingredientDetails.copy(weight = value)))
    }

    fun updatePrice(value: String){
        _ingredientUiState.value =
            _ingredientUiState.value.copy(ingredientDetails = _ingredientUiState.value.ingredientDetails.copy(price = value), isEntryValid = validateInput(_ingredientUiState.value.ingredientDetails.copy(price = value)))
    }

    private fun validateInput(uiState: IngredientDetails = _ingredientUiState.value.ingredientDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && manufacturer.isNotBlank() && price.isNotBlank() && weight.isNotBlank() && energy.isNotBlank() && protein.isNotBlank() && fat.isNotBlank() && carbohydrates.isNotBlank()
        }
    }

    suspend fun saveItem() {
        if (validateInput()) {
            if (ingredientId == -1) recipeRepository.insertIngredient(_ingredientUiState.value.ingredientDetails.toIngredient())
            else recipeRepository.updateIngredient(_ingredientUiState.value.ingredientDetails.toIngredient())
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                val savedStateHandle = extras.createSavedStateHandle()

                return IngredientViewModel(
                    recipeRepository = (application as NettoApplication).container.recipeRepository,
                    savedStateHandle = savedStateHandle
                ) as T
            }
        }
    }
}
