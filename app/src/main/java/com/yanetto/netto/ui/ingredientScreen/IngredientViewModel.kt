package com.yanetto.netto.ui.ingredientScreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class IngredientViewModel : ViewModel(){
    private val _uiState = MutableStateFlow(IngredientUiState())
    val uiState: StateFlow<IngredientUiState> = _uiState.asStateFlow()
}