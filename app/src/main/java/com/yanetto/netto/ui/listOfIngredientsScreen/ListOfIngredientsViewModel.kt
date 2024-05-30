package com.yanetto.netto.ui.listOfIngredientsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.yanetto.netto.NettoApplication
import com.yanetto.netto.data.RecipeRepository
import com.yanetto.netto.model.Ingredient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext

class ListOfIngredientsViewModel(
    private val recipeRepository: RecipeRepository
): ViewModel() {
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val allIngredients: StateFlow<List<Ingredient>> = recipeRepository.getAllIngredientsStream()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = emptyList()
        )

    val ingredientsUiState: StateFlow<ListOfIngredientsUiState> = combine(_query, allIngredients) { query, ingredients ->
        val filteredIngredients = if (query.isEmpty()) {
            ingredients
        } else {
            ingredients.filter { it.name.contains(query, ignoreCase = true) }
        }
        ListOfIngredientsUiState(filteredIngredients)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = ListOfIngredientsUiState()
    )

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NettoApplication)
                val recipeRepository = application.container.recipeRepository
                ListOfIngredientsViewModel(recipeRepository= recipeRepository)
            }
        }
    }

    suspend fun deleteIngredient(id: Int){
        withContext(Dispatchers.IO) {
            recipeRepository.deleteIngredient(recipeRepository.getIngredient(id))
        }
    }

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }
}
