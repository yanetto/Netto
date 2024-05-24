package com.yanetto.netto.ui.editRecipeScreen

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
import com.yanetto.netto.model.IngredientRecipe
import com.yanetto.netto.model.IngredientWithWeight
import kotlinx.coroutines.launch

class EditRecipeViewModel(
    private val recipeRepository: RecipeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var recipeUiState by mutableStateOf(EditRecipeUiState())

    private val recipeId: Int = savedStateHandle[EditRecipeDetailsDestination.recipeIdArg] ?: -1
    init {
        if (recipeId != -1){
            viewModelScope.launch {
                    recipeUiState = recipeUiState.copy(recipeDetails = recipeRepository.getRecipe(recipeId).toRecipeDetails())

                    recipeRepository.getIngredientsWithWeights(recipeId)
                        .collect { ingredientsWithWeights ->
                            recipeUiState = recipeUiState.copy(listOfIngredients = ingredientsWithWeights)
                        }
            }
        }
    }

    fun updateUiState(recipeDetails: RecipeDetails) {
        recipeUiState =
            EditRecipeUiState(recipeDetails = recipeDetails, isEntryValid = validateInput(recipeDetails))
    }

    fun updateIngredientWeight(ingredient: Ingredient, weight: Float){
        var ind : Int
        var list = recipeUiState.listOfIngredients.toMutableList()
        for (i in list.indices){
            if (list[i].ingredient == ingredient) {
                list[i] = IngredientWithWeight(ingredient, weight)
                recipeUiState = recipeUiState.copy(listOfIngredients = list)
                break
            }
        }
    }

    private fun validateInput(uiState: RecipeDetails = recipeUiState.recipeDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && description.isNotBlank() && servingsCount.isNotBlank()
        }
    }

    suspend fun getIngredientList(): List<Ingredient> {
        var ingredientList: List<Ingredient> = listOf()
        recipeRepository.getAllIngredientsStream().collect {ingredients ->
            ingredientList = ingredients
        }
        return ingredientList
    }

    suspend fun saveItem() {
        if (validateInput()) {
            if (recipeId == -1) recipeRepository.insertRecipe(recipeUiState.recipeDetails.toRecipe())
            else recipeRepository.updateRecipe(recipeUiState.recipeDetails.toRecipe())
        }
    }

    suspend fun addIngredient(ingredient: Ingredient, weight: Float){
        recipeRepository.insertRecipeWithIngredients(IngredientRecipe(ingredient.id, recipeId, weight))
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

                return EditRecipeViewModel(
                    recipeRepository = (application as NettoApplication).container.recipeRepository,
                    savedStateHandle = savedStateHandle
                ) as T
            }
        }
    }
}