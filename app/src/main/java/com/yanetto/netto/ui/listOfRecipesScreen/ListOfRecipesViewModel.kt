package com.yanetto.netto.ui.listOfRecipesScreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ListOfRecipesViewModel:ViewModel() {
    private val _uiState = MutableStateFlow(ListOfRecipesUiState(null))
    val uiState: StateFlow<ListOfRecipesUiState> = _uiState.asStateFlow()
}