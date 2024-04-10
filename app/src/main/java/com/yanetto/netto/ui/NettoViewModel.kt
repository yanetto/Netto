package com.yanetto.netto.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.math.RoundingMode

class NettoViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(NettoUiState())
    val uiState: StateFlow<NettoUiState> = _uiState.asStateFlow()

    fun changeServingCount(increment: Boolean){
        val k: Float
        val servingsCount = _uiState.value.recipes[uiState.value.currentRecipePosition].servingsCount
        if (increment){
            k = (servingsCount + 1f) / servingsCount
            _uiState.value.recipes[uiState.value.currentRecipePosition].servingsCount += 1
        }
        else {
            k = (servingsCount - 1f) / servingsCount
            _uiState.value.recipes[uiState.value.currentRecipePosition].servingsCount -= 1
        }

        calculateIngredientWeight(k)
        calculateWeightAndPrice(k)

        println("\nUPDATED ${_uiState.value.recipes[uiState.value.currentRecipePosition].ingredientList[0].weight} \n")
    }

    private fun calculateIngredientWeight(k: Float){
        _uiState.value.recipes[uiState.value.currentRecipePosition].ingredientList.forEach {
            it.weight *= k
            it.weight = it.weight.toBigDecimal().setScale(1, RoundingMode.UP).toFloat()
        }
    }
    private fun calculateWeightAndPrice(k: Float){
        _uiState.value.recipes[uiState.value.currentRecipePosition].totalWeight *= k
        _uiState.value.recipes[uiState.value.currentRecipePosition].totalWeight = _uiState.value.recipes[uiState.value.currentRecipePosition]
            .totalWeight.toBigDecimal().setScale(1, RoundingMode.UP).toFloat()
        _uiState.value.recipes[uiState.value.currentRecipePosition].totalPrice *= k
        _uiState.value.recipes[uiState.value.currentRecipePosition].totalPrice = _uiState.value.recipes[uiState.value.currentRecipePosition]
            .totalPrice.toBigDecimal().setScale(1, RoundingMode.UP).toFloat()
    }
}