package com.yanetto.netto.ui.ingredientScreen

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yanetto.netto.R
import com.yanetto.netto.ui.theme.NettoTheme

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
            Label(labelText = "Ingredient")

            Card(
                modifier = Modifier.padding(8.dp)
            ){
                Column (modifier = Modifier.padding(8.dp)){
                    IngredientTextField(stringResource(R.string.name))

                    IngredientTextField(stringResource(R.string.description))

                    IngredientTextField("Weight (g)")

                    IngredientTextField("Price (rub)")
                }


            }

            Text(
                text = stringResource(id = R.string.nutritional_info),
                style = MaterialTheme.typography.titleMedium,
                modifier = modifier.padding(8.dp)
            )


            Card(
                modifier = Modifier.padding(8.dp)
            ){
                Column (modifier = Modifier.padding(8.dp)) {
                    IngredientTextField("Energy (kcal)")
                    IngredientTextField("Protein (g)")
                    IngredientTextField("Fat (g)")
                    IngredientTextField("Carbohydrates (g)")
                }
            }


        }
    }
    Column (modifier = modifier
        .fillMaxWidth()
        .padding(8.dp),
        verticalArrangement = Arrangement.Bottom
    ){
        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                enabled = true,
                onClick = { },
                modifier = Modifier
                    .padding(0.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.done_black_48dp),
                    contentDescription = null,
                    modifier = Modifier,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
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

    Box(
        modifier = modifier.padding(start = 8.dp, bottom = 8.dp, end = 8.dp)
    ){
        OutlinedTextField(
            value = textValue,
            onValueChange = {
                textValue = it
            },
            //modifier = Modifier.padding(0.dp),
            label = {
                Text(
                    text = hint
                )
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Start, color = hintColor),
            interactionSource = interactionSource,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            keyboardActions = KeyboardActions(onDone  = {}),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = hintColor,
                containerColor = MaterialTheme.colorScheme.background,
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedBorderColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}

@Preview
@Composable
fun IngredientScreenPreview(){
    NettoTheme {
        Surface (
            modifier = Modifier.fillMaxSize()
        ){
            IngredientScreen()
        }
    }
}