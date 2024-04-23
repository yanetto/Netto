package com.yanetto.netto.ui.ingredientScreen

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yanetto.netto.R

@Composable
fun IngredientScreen(
    modifier: Modifier = Modifier,
    ingredientViewModel: IngredientViewModel = viewModel()
){
    val ingredientUiState by ingredientViewModel.uiState.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp)
            .scrollable(
                orientation = Orientation.Vertical,
                state = rememberScrollState()
            )
    ){
        item { 
            Label(labelText = stringResource(R.string.name))

            IngredientTextField(stringResource(R.string.name))
            
        }
    }
}

@Composable
fun Label(
    modifier: Modifier = Modifier,
    labelText: String
){
    Text(
        text = labelText,
        style = MaterialTheme.typography.headlineMedium,
        modifier = modifier.padding(8.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientTextField(
    hint: String,
    modifier: Modifier = Modifier
){
    var textValue by remember{ mutableStateOf(TextFieldValue("")) }
    val interactionSource = remember{ MutableInteractionSource() }
    val isFocused = interactionSource.collectIsFocusedAsState()
    
    val color = if(textValue.text.toFloatOrNull() != null || textValue.text.isEmpty()) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.errorContainer
    val hintColor = if(isFocused.value) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f) else MaterialTheme.colorScheme.onSurface

    OutlinedTextField(
        value = textValue,
        onValueChange = {
            textValue = it
        },
        label = {
            Text(
                text = hint,
                color = hintColor
            )
        },
        singleLine = true,
        modifier = Modifier,
        textStyle = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.End, color = color),
        interactionSource = interactionSource,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        keyboardActions = KeyboardActions(onDone  = {})
    )
}