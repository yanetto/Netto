package com.yanetto.netto.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NettoViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(NettoUiState(null))
    val uiState: StateFlow<NettoUiState> = _uiState.asStateFlow()
}