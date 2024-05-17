package com.yanetto.netto.ui.listOfIngredientsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.yanetto.netto.NettoApplication
import com.yanetto.netto.data.RecipeRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ListOfIngredientsViewModel(
    private val recipeRepository: RecipeRepository
): ViewModel() {
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NettoApplication)
                val recipeRepository = application.container.recipeRepository
                ListOfIngredientsViewModel(recipeRepository= recipeRepository)
            }
        }
    }

    val ingredientsUiState: StateFlow<ListOfIngredientsUiState> =
        recipeRepository.getAllIngredientsStream().map { ListOfIngredientsUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = ListOfIngredientsUiState()
            )

    suspend fun deleteIngredient(id: Int){
        recipeRepository.deleteIngredient(recipeRepository.getIngredient(id))
    }
}
