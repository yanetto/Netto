package com.yanetto.netto.ui.listOfRecipesScreen

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

class ListOfRecipesViewModel(
    private val recipeRepository: RecipeRepository
):ViewModel() {
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NettoApplication)
                val recipeRepository = application.container.recipeRepository
                ListOfRecipesViewModel(recipeRepository= recipeRepository)
            }
        }
    }

    val recipeUiState: StateFlow<ListOfRecipesUiState> =
        recipeRepository.getAllRecipesStream().map { ListOfRecipesUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = ListOfRecipesUiState()
            )

    suspend fun deleteRecipe(id: Int){
        recipeRepository.deleteRecipe(recipeRepository.getRecipe(id))
    }
}