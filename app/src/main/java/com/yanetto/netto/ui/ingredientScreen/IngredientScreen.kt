@file:OptIn(ExperimentalMaterial3Api::class)

package com.yanetto.netto.ui.ingredientScreen

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yanetto.netto.R
import com.yanetto.netto.ui.theme.NettoTheme
import kotlinx.coroutines.launch

@Composable
fun IngredientScreen(
    modifier: Modifier = Modifier,
    ingredientViewModel: IngredientViewModel = viewModel(factory = IngredientViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()

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
            Row {
                Label(labelText = "Ingredient")
                Icon(
                    painter = painterResource(id = R.drawable.add_a_photo_24dp),
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            IngredientInputForm(
                ingredientDetails = ingredientViewModel.ingredientUiState.ingredientDetails,
                onValueChange = ingredientViewModel::updateUiState
            )
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
                onClick = { coroutineScope.launch {ingredientViewModel.saveItem()} },
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

@Composable
fun IngredientInputForm(
    modifier: Modifier = Modifier,
    ingredientDetails: IngredientDetails,
    onValueChange: (IngredientDetails) -> Unit = {}
){

    val hintColor = MaterialTheme.colorScheme.onSurface

        Column (modifier = Modifier.padding(8.dp)){
            OutlinedTextField(
                value = ingredientDetails.name,
                onValueChange = { onValueChange(ingredientDetails.copy(name = it)) },
                //modifier = Modifier.padding(0.dp),
                label = {
                    Text(
                        text = stringResource(id = R.string.name)
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                textStyle = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Start, color = hintColor),
                //interactionSource = interactionSource,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                keyboardActions = KeyboardActions(onDone  = {}),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedBorderColor = MaterialTheme.colorScheme.primary
                )
            )

            OutlinedTextField(
                value = ingredientDetails.manufacturer,
                onValueChange = { onValueChange(ingredientDetails.copy(manufacturer = it)) },
                //modifier = Modifier.padding(0.dp),
                label = {
                    Text(
                        text = stringResource(R.string.manufacturer)
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                textStyle = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Start, color = hintColor),
                //interactionSource = interactionSource,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                keyboardActions = KeyboardActions(onDone  = {}),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedBorderColor = MaterialTheme.colorScheme.primary
                )
            )

            OutlinedTextField(
                value = ingredientDetails.weight,
                onValueChange = { onValueChange(ingredientDetails.copy(weight = it)) },
                //modifier = Modifier.padding(0.dp),
                label = {
                    Text(
                        text = "Weight(g)"
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                textStyle = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Start, color = hintColor),
                //interactionSource = interactionSource,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                keyboardActions = KeyboardActions(onDone  = {}),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedBorderColor = MaterialTheme.colorScheme.primary
                )
            )

            OutlinedTextField(
                value = ingredientDetails.price,
                onValueChange = { onValueChange(ingredientDetails.copy(price = it)) },
                //modifier = Modifier.padding(0.dp),
                label = {
                    Text(
                        text = "Price(rub)"
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                textStyle = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Start, color = hintColor),
                //interactionSource = interactionSource,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                keyboardActions = KeyboardActions(onDone  = {}),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedBorderColor = MaterialTheme.colorScheme.primary
                )
            )



    }

    Text(
        text = stringResource(id = R.string.nutritional_info),
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier.padding(8.dp)
    )



        Column (modifier = Modifier.padding(8.dp)) {
            OutlinedTextField(
                value = ingredientDetails.energy,
                onValueChange = { onValueChange(ingredientDetails.copy(energy = it)) },
                //modifier = Modifier.padding(0.dp),
                label = {
                    Text(
                        text = "Energy(kcal)"
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                textStyle = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Start, color = hintColor),
                //interactionSource = interactionSource,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                keyboardActions = KeyboardActions(onDone  = {}),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedBorderColor = MaterialTheme.colorScheme.primary
                )
            )

            OutlinedTextField(
                value = ingredientDetails.protein,
                onValueChange = { onValueChange(ingredientDetails.copy(protein = it)) },
                //modifier = Modifier.padding(0.dp),
                label = {
                    Text(
                        text = "Protein(g)"
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                textStyle = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Start, color = hintColor),
                //interactionSource = interactionSource,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                keyboardActions = KeyboardActions(onDone  = {}),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedBorderColor = MaterialTheme.colorScheme.primary
                )
            )

            OutlinedTextField(
                value = ingredientDetails.fat,
                onValueChange = { onValueChange(ingredientDetails.copy(fat = it)) },
                //modifier = Modifier.padding(0.dp),
                label = {
                    Text(
                        text = "Fat(g)"
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                textStyle = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Start, color = hintColor),
                //interactionSource = interactionSource,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                keyboardActions = KeyboardActions(onDone  = {}),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedBorderColor = MaterialTheme.colorScheme.primary
                )
            )

            OutlinedTextField(
                value = ingredientDetails.carbohydrates,
                onValueChange = { onValueChange(ingredientDetails.copy(carbohydrates = it)) },
                //modifier = Modifier.padding(0.dp),
                label = {
                    Text(
                        text = "Carbohydrates(g)"
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                textStyle = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Start, color = hintColor),
                //interactionSource = interactionSource,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                keyboardActions = KeyboardActions(onDone  = {}),
                colors = TextFieldDefaults.outlinedTextFieldColors(
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