package com.yanetto.netto.ui.listOfRecipesScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.yanetto.netto.NettoApplication
import com.yanetto.netto.data.RecipeRepository
import com.yanetto.netto.model.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext

class ListOfRecipesViewModel(
    private val recipeRepository: RecipeRepository
):ViewModel() {
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val allRecipes: StateFlow<List<Recipe>> = recipeRepository.getAllRecipesStream()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = emptyList()
        )

    val recipeUiState: StateFlow<ListOfRecipesUiState> = combine(_query, allRecipes) { query, recipes ->
        val filteredRecipes = if (query.isEmpty()) {
            recipes
        } else {
            recipes.filter { it.name.contains(query, ignoreCase = true) }
        }
        ListOfRecipesUiState(filteredRecipes)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = ListOfRecipesUiState()
    )


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NettoApplication)
                val recipeRepository = application.container.recipeRepository
                ListOfRecipesViewModel(recipeRepository= recipeRepository)
            }
        }
    }

    suspend fun deleteRecipe(id: Int){
        withContext(Dispatchers.IO) {
            recipeRepository.deleteRecipe(recipeRepository.getRecipe(id))
            recipeRepository.deleteIngredientsFromRecipe(id)
        }
    }

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }
}