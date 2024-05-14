@file:OptIn(ExperimentalMaterial3Api::class)

package com.yanetto.netto.ui.ingredientScreen

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
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
import kotlinx.coroutines.launch

@Composable
fun IngredientScreen(
    modifier: Modifier = Modifier,
    ingredientViewModel: IngredientViewModel = viewModel(factory = IngredientViewModel.Factory),
){
    val coroutineScope = rememberCoroutineScope()

    Column {
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            IconButton(
                enabled = true,
                onClick = {  },
                modifier = Modifier
                    .size(48.dp)
            ){
                Icon(
                    painter = painterResource(id = R.drawable.add_a_photo_24dp),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            IconButton(
                enabled = true,
                onClick = { coroutineScope.launch {ingredientViewModel.saveItem()} },
                modifier = Modifier
                    .size(48.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.done_24dp),
                    contentDescription = null,
                    modifier = Modifier,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .scrollable(
                    orientation = Orientation.Vertical,
                    state = rememberScrollState()
                )
        ){
            item {
                Spacer(modifier = Modifier.height(128.dp))
                ElevatedCard (
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 12.dp)
                ){
                    IngredientInputForm(
                        ingredientDetails = ingredientViewModel.ingredientUiState.ingredientDetails,
                        onValueChange = ingredientViewModel::updateUiState
                    )
                    Label(modifier = Modifier.padding(horizontal = 8.dp), labelText = stringResource(id = R.string.nutritional_info))
                    NutritionalIngredientItems(modifier = Modifier.padding(horizontal = 8.dp), param = stringResource(R.string.energy), info = " kcal", ingredientDetail = ingredientViewModel.ingredientUiState.ingredientDetails.energy, onValueChange = ingredientViewModel::updateEnergy)
                    NutritionalIngredientItems(modifier = Modifier.padding(horizontal = 8.dp), param = stringResource(R.string.protein), info = " g", ingredientDetail = ingredientViewModel.ingredientUiState.ingredientDetails.protein, onValueChange = ingredientViewModel::updateProtein)
                    NutritionalIngredientItems(modifier = Modifier.padding(horizontal = 8.dp), param = stringResource(R.string.fat), info = " g", ingredientDetail = ingredientViewModel.ingredientUiState.ingredientDetails.fat, onValueChange = ingredientViewModel::updateFat)
                    NutritionalIngredientItems(modifier = Modifier.padding(horizontal = 8.dp), param = stringResource(R.string.carbohydrates), info = " g", ingredientDetail = ingredientViewModel.ingredientUiState.ingredientDetails.carbohydrates, onValueChange = ingredientViewModel::updateCarbohydrates)
                    HorizontalDivider(
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                            .fillMaxWidth()
                    )
                }

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
        style = MaterialTheme.typography.headlineLarge,
        modifier = modifier.padding(8.dp)
    )
}

@Composable
fun IngredientInputForm(
    modifier: Modifier = Modifier,
    ingredientDetails: IngredientDetails,
    onValueChange: (IngredientDetails) -> Unit = {}
){

    Column (
        modifier = modifier.padding(horizontal = 16.dp, vertical = 16.dp)
    ) {

        OutlinedTextField(
            value = ingredientDetails.name,
            onValueChange = { onValueChange(ingredientDetails.copy(name = it)) },
            label = {
                Text(
                    text = stringResource(id = R.string.name)
                )
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            textStyle = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Start),
            //interactionSource = interactionSource,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            keyboardActions = KeyboardActions(onDone  = {}),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.primary
            )
        )

        OutlinedTextField(
            value = ingredientDetails.manufacturer,
            onValueChange = { onValueChange(ingredientDetails.copy(manufacturer = it)) },
            label = {
                Text(
                    text = stringResource(id = R.string.manufacturer)
                )
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            textStyle = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Start),
            //interactionSource = interactionSource,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            keyboardActions = KeyboardActions(onDone  = {}),
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )

        OutlinedTextField(
            value = ingredientDetails.weight,
            onValueChange = { onValueChange(ingredientDetails.copy(weight = it)) },
            label = {
                Text(
                    text = stringResource(R.string.weight_g),
                    textAlign = TextAlign.End
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            singleLine = true,
            textStyle = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Start),
            //interactionSource = interactionSource,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            keyboardActions = KeyboardActions(onDone  = {}),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.primary
            )
        )

        OutlinedTextField(
            value = ingredientDetails.price,
            onValueChange = { onValueChange(ingredientDetails.copy(price = it)) },
            label = {
                Text(
                    text = stringResource(R.string.price_rub)
                )
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            textStyle = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Start),
            //interactionSource = interactionSource,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            keyboardActions = KeyboardActions(onDone  = {}),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.primary
            )
        )

    }
}



@Composable
fun NutritionalIngredientItems(
    modifier: Modifier = Modifier,
    param: String,
    info: String,
    ingredientDetail: String,
    onValueChange: (String) -> Unit = {}
){
    HorizontalDivider(
        modifier = modifier
            .padding(start = 8.dp, end = 8.dp)
            .fillMaxWidth()
            .width(1.dp)
    )
    val focusManager = LocalFocusManager.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = param,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(2f)
        )

        Spacer(modifier = Modifier.size(16.dp))

        var textValue by remember{ mutableStateOf(TextFieldValue("")) }
        val interactionSource = remember{ MutableInteractionSource() }
        val isFocused = interactionSource.collectIsFocusedAsState()
        val valueHint = if (ingredientDetail == "") "0.0" else ingredientDetail

        LaunchedEffect(isFocused.value){
            if(!isFocused.value) textValue = TextFieldValue("")
        }

        val color = if(textValue.text.toFloatOrNull() != null || textValue.text.isEmpty()) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.errorContainer
        val hintColor = if(isFocused.value) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f) else MaterialTheme.colorScheme.onSurface

        BasicTextField(
            value = textValue,
            onValueChange = {
                textValue = it
                onValueChange(it.text) },
            singleLine = true,
            modifier = Modifier
                .weight(1f),
            textStyle = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.End, color = color),
            interactionSource = interactionSource,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            keyboardActions = KeyboardActions(onDone  = {
                focusManager.clearFocus()
            }),
            cursorBrush = SolidColor(color)
        ){
            Box(
                modifier = Modifier.fillMaxWidth()
            ){
                if(textValue.text.isEmpty()){
                    Text(
                        text = valueHint,
                        style = MaterialTheme.typography.titleMedium,
                        color = hintColor,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }
                it()
            }
        }

        Text(
            text = info,
            style = MaterialTheme.typography.titleMedium,
            color = color
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