package com.yanetto.netto.ui.editRecipeScreen

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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditRecipeViewModel(
    private val recipeRepository: RecipeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _recipeUiState = MutableStateFlow(EditRecipeUiState())
    val recipeUiState: StateFlow<EditRecipeUiState> = _recipeUiState

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val recipeId: Int = savedStateHandle[EditRecipeDetailsDestination.recipeIdArg] ?: -1

    init {
        if (recipeId != -1) {
            viewModelScope.launch {
                val recipeDetails = recipeRepository.getRecipe(recipeId).toRecipeDetails()
                _recipeUiState.value = _recipeUiState.value.copy(recipeDetails = recipeDetails)

                recipeRepository.getIngredientsWithWeights(recipeId)
                    .collect { ingredientsWithWeights ->
                        _recipeUiState.value =
                            _recipeUiState.value.copy(listOfIngredients = ingredientsWithWeights)
                    }
            }
        }

        viewModelScope.launch {
            recipeRepository.getAllIngredientsStream()
                .collect { allIngredients ->
                    _recipeUiState.value = _recipeUiState.value.copy(allIngredientList = allIngredients)
                }
        }

        viewModelScope.launch {
            combine(
                recipeRepository.getAllIngredientsStream(),
                _query
            ) { allIngredients, query ->
                val filteredIngredients = allIngredients.filter {
                    it.name.contains(query, ignoreCase = true)
                }
                _recipeUiState.value = _recipeUiState.value.copy(
                    allIngredientList = filteredIngredients
                )
            }.collect{}
        }
    }

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }

    fun updateUiState(recipeDetails: RecipeDetails) {
        _recipeUiState.value = _recipeUiState.value.copy(recipeDetails = recipeDetails, isEntryValid = validateInput(recipeDetails))
    }

    fun updateIngredientWeight(ingredient: Ingredient, weight: Float){
        val list = _recipeUiState.value.listOfIngredients.toMutableList()
        for (i in list.indices){
            if (list[i].ingredient == ingredient) {
                list[i] = IngredientWithWeight(ingredient, weight)
                _recipeUiState.value = _recipeUiState.value.copy(listOfIngredients = list)
                break
            }
        }
    }

    private fun validateInput(uiState: RecipeDetails = _recipeUiState.value.recipeDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && description.isNotBlank() && servingsCount.isNotBlank()
        }
    }

    suspend fun saveItem() {
        if (validateInput()) {
            withContext(Dispatchers.IO) {
                if (recipeId == -1) {
                    recipeRepository.insertRecipe(_recipeUiState.value.recipeDetails.toRecipe())
                    val id = recipeRepository.getAllRecipesStream().first().maxBy { it.id }.id
                    _recipeUiState.value.listOfIngredients.forEach {
                        recipeRepository.insertRecipeWithIngredients(
                            IngredientRecipe(
                                it.ingredient.id,
                                id,
                                it.ingredientWeight
                            )
                        )
                    }
                } else{
                    recipeRepository.updateRecipe(_recipeUiState.value.recipeDetails.toRecipe())
                    recipeRepository.deleteIngredientsFromRecipe(_recipeUiState.value.recipeDetails.id)
                    _recipeUiState.value.listOfIngredients.forEach {
                        recipeRepository.insertRecipeWithIngredients(
                            IngredientRecipe(
                                it.ingredient.id,
                                _recipeUiState.value.recipeDetails.id,
                                it.ingredientWeight
                            )
                        )
                    }
                }
            }
        }
    }

    fun isIngredientInRecipe(ingredient: Ingredient): Boolean{
        _recipeUiState.value.listOfIngredients.forEach {
            if(it.ingredient == ingredient) return true
        }
        return false
    }

    fun editIngredient(isIngredientInRecipe: Boolean, ingredient: Ingredient){
        if (isIngredientInRecipe) deleteIngredientFromRecipe(ingredient)
        else addIngredientToRecipe(ingredient)
    }

    private fun deleteIngredientFromRecipe(ingredient: Ingredient){
        val newList: MutableList<IngredientWithWeight> = _recipeUiState.value.listOfIngredients.toMutableList()
        newList.removeIf { it.ingredient == ingredient }
        _recipeUiState.value = _recipeUiState.value.copy(listOfIngredients = newList)
    }

    private fun addIngredientToRecipe(ingredient: Ingredient){
        val newList: MutableList<IngredientWithWeight> = _recipeUiState.value.listOfIngredients.toMutableList()
        newList.add(IngredientWithWeight(ingredient, 100f))
        _recipeUiState.value = _recipeUiState.value.copy(listOfIngredients = newList)
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