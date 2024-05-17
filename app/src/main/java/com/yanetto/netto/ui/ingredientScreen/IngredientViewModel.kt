package com.yanetto.netto.ui.ingredientScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import com.yanetto.netto.NettoApplication
import com.yanetto.netto.data.RecipeRepository
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.launch

class IngredientViewModel(
    private val recipeRepository: RecipeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var ingredientUiState by mutableStateOf(IngredientUiState())

    private val ingredientId: Int = savedStateHandle[IngredientDetailsDestination.ingredientIdArg] ?: -1

    init {
        if (ingredientId != -1){
            viewModelScope.launch {
                val ingredient = recipeRepository.getIngredient(ingredientId)
                ingredientUiState = ingredientUiState.copy(ingredientDetails = ingredient.toIngredientDetails())

            }
        }
    }

//    val uiState: StateFlow<IngredientUiState> =
//        recipeRepository.getItemStream(ingredientId)
//            .filterNotNull()
//            .map {
//                IngredientUiState(
//                    ingredientDetails = it.toIngredientDetails(),
//                    isEntryValid = validateInput(it.toIngredientDetails())
//                )
//            }.stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(5_000L),
//                initialValue = IngredientUiState()
//            )

    fun updateUiState(ingredientDetails: IngredientDetails) {
        ingredientUiState =
            IngredientUiState(ingredientDetails = ingredientDetails, isEntryValid = validateInput(ingredientDetails))
    }

    fun updateEnergy(value: String){
        ingredientUiState =
            IngredientUiState(ingredientDetails = ingredientUiState.ingredientDetails.copy(energy = value), isEntryValid = validateInput(ingredientUiState.ingredientDetails.copy(energy = value)))
    }

    fun updateProtein(value: String){
        ingredientUiState =
            IngredientUiState(ingredientDetails = ingredientUiState.ingredientDetails.copy(protein = value), isEntryValid = validateInput(ingredientUiState.ingredientDetails.copy(protein = value)))
    }

    fun updateFat(value: String){
        ingredientUiState =
            IngredientUiState(ingredientDetails = ingredientUiState.ingredientDetails.copy(fat = value), isEntryValid = validateInput(ingredientUiState.ingredientDetails.copy(fat = value)))
    }

    fun updateCarbohydrates(value: String){
        ingredientUiState =
            IngredientUiState(ingredientDetails = ingredientUiState.ingredientDetails.copy(carbohydrates = value), isEntryValid = validateInput(ingredientUiState.ingredientDetails.copy(carbohydrates = value)))
    }

    fun updateWeight(value: String){
        ingredientUiState =
            IngredientUiState(ingredientDetails = ingredientUiState.ingredientDetails.copy(weight = value), isEntryValid = validateInput(ingredientUiState.ingredientDetails.copy(weight = value)))
    }

    fun updatePrice(value: String){
        ingredientUiState =
            IngredientUiState(ingredientDetails = ingredientUiState.ingredientDetails.copy(price = value), isEntryValid = validateInput(ingredientUiState.ingredientDetails.copy(price = value)))
    }

    private fun validateInput(uiState: IngredientDetails = ingredientUiState.ingredientDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && manufacturer.isNotBlank() && price.isNotBlank() && weight.isNotBlank() && energy.isNotBlank() && protein.isNotBlank() && fat.isNotBlank() && carbohydrates.isNotBlank()
        }
    }

    suspend fun saveItem() {
        if (validateInput()) {
            if (ingredientId == -1) recipeRepository.insertIngredient(ingredientUiState.ingredientDetails.toIngredient())
            else recipeRepository.updateIngredient(ingredientUiState.ingredientDetails.toIngredient())
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
