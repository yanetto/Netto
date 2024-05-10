@file:OptIn(ExperimentalMaterial3Api::class)

package com.yanetto.netto.ui.ingredientScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
            .scrollable(
                orientation = Orientation.Vertical,
                state = rememberScrollState()
            )
    ){
        item {
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

            Spacer(modifier = Modifier.height(128.dp))
            
            Card (
                shape = RoundedCornerShape(24.dp)
            ){

                IngredientInputForm(
                    ingredientDetails = ingredientViewModel.ingredientUiState.ingredientDetails,
                    onValueChange = ingredientViewModel::updateUiState
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
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        val hintColor = MaterialTheme.colorScheme.onSurface

        TextField(
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
            textStyle = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Start, color = hintColor),
            //interactionSource = interactionSource,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            keyboardActions = KeyboardActions(onDone  = {}),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                //containerColor = MaterialTheme.colorScheme.background,
                cursorColor = MaterialTheme.colorScheme.primary
            )
        )

        TextField(
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
            textStyle = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Start, color = hintColor),
            //interactionSource = interactionSource,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            keyboardActions = KeyboardActions(onDone  = {}),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                //containerColor = MaterialTheme.colorScheme.background,
                cursorColor = MaterialTheme.colorScheme.primary
            )
        )

        Row(
            modifier = Modifier
                .padding(vertical = 4.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "Weight",
                modifier = Modifier.padding(top = 24.dp, start = 16.dp),
                textAlign = TextAlign.Start
            )
            //Spacer(modifier = Modifier.size(128.dp))
            Row{
                TextField(
                    value = ingredientDetails.weight,
                    onValueChange = { onValueChange(ingredientDetails.copy(weight = it)) },
                    label = {
                        Text(
                            text = "",
                            textAlign = TextAlign.End
                        )
                    },
                    modifier = Modifier.width(96.dp),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.End, color = hintColor),
                    //interactionSource = interactionSource,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    keyboardActions = KeyboardActions(onDone  = {}),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        //containerColor = MaterialTheme.colorScheme.background,
                        cursorColor = MaterialTheme.colorScheme.primary
                    )
                )
                Text(
                    text = " g",
                    modifier = Modifier
                        .padding(top = 24.dp, end = 16.dp)
                        .width(36.dp),
                    textAlign = TextAlign.End
                )
            }

        }

        Row(
            modifier = Modifier
                .padding(vertical = 4.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "Price",
                modifier = Modifier.padding(top = 24.dp, start = 16.dp),
                textAlign = TextAlign.Start
            )
            //Spacer(modifier = Modifier.size(128.dp))
            Row{
                TextField(
                    value = ingredientDetails.price,
                    onValueChange = { onValueChange(ingredientDetails.copy(price = it)) },
                    label = {
                        Text(
                            text = ""
                        )
                    },
                    singleLine = true,
                    modifier = Modifier
                            .width(96.dp),
                    textStyle = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.End, color = hintColor),
                    //interactionSource = interactionSource,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    keyboardActions = KeyboardActions(onDone  = {}),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        //containerColor = MaterialTheme.colorScheme.background,
                        cursorColor = MaterialTheme.colorScheme.primary
                    )
                )
                Text(
                    text = " rub",
                    modifier = Modifier
                        .padding(top = 24.dp, end = 16.dp)
                        .width(36.dp),
                    textAlign = TextAlign.End
                )
            }
        }
    }

    Card(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp), shape = RoundedCornerShape(4.dp), border = BorderStroke(4.dp, MaterialTheme.colorScheme.onSurface)) {
        Label(modifier = Modifier.padding(start = 16.dp), labelText =  stringResource(id = R.string.nutritional_info))

        Column (modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 16.dp)) {
            val hintColor = MaterialTheme.colorScheme.onSurface

            HorizontalDivider(
                color = MaterialTheme.colorScheme.onSurface,
                thickness = 12.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp)
            )

            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Avg. Quantity Per 100g",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.End,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            HorizontalDivider(
                thickness = 8.dp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp)
            )


            Row(
                modifier = Modifier
                    .padding(bottom = 4.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = "Energy",
                    modifier = Modifier.padding(top = 24.dp, start = 16.dp),
                    textAlign = TextAlign.Start
                )
                //Spacer(modifier = Modifier.size(128.dp))
                Row{
                    TextField(
                        value = ingredientDetails.energy,
                        onValueChange = { onValueChange(ingredientDetails.copy(energy = it)) },
                        label = {
                            Text(
                                text = ""
                            )
                        },
                        singleLine = true,
                        modifier = Modifier
                            .width(96.dp),
                        textStyle = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.End, color = hintColor),
                        //interactionSource = interactionSource,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        keyboardActions = KeyboardActions(onDone  = {}),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            //containerColor = MaterialTheme.colorScheme.background,
                            cursorColor = MaterialTheme.colorScheme.primary
                        )
                    )
                    Text(
                        text = " kcal",
                        modifier = Modifier
                            .padding(top = 24.dp, end = 16.dp)
                            .width(36.dp),
                        textAlign = TextAlign.End
                    )
                }
            }

            HorizontalDivider(
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp)
            )

            Row(
                modifier = Modifier
                    .padding(bottom = 4.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = "Protein",
                    modifier = Modifier.padding(top = 24.dp, start = 16.dp),
                    textAlign = TextAlign.Start
                )
                //Spacer(modifier = Modifier.size(128.dp))
                Row{
                    TextField(
                        value = ingredientDetails.protein,
                        onValueChange = { onValueChange(ingredientDetails.copy(protein = it)) },
                        label = {
                            Text(
                                text = ""
                            )
                        },
                        singleLine = true,
                        modifier = Modifier
                            .width(96.dp),
                        textStyle = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.End, color = hintColor),
                        //interactionSource = interactionSource,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        keyboardActions = KeyboardActions(onDone  = {}),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            //containerColor = MaterialTheme.colorScheme.background,
                            cursorColor = MaterialTheme.colorScheme.primary
                        )
                    )
                    Text(
                        text = " g",
                        modifier = Modifier
                            .padding(top = 24.dp, end = 16.dp)
                            .width(36.dp),
                        textAlign = TextAlign.End
                    )
                }
            }

            HorizontalDivider(
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp)
            )

            Row(
                modifier = Modifier
                    .padding(bottom = 4.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = "Fat",
                    modifier = Modifier.padding(top = 24.dp, start = 16.dp),
                    textAlign = TextAlign.Start
                )
                //Spacer(modifier = Modifier.size(128.dp))
                Row{
                    TextField(
                        value = ingredientDetails.fat,
                        onValueChange = { onValueChange(ingredientDetails.copy(fat = it)) },
                        label = {
                            Text(
                                text = ""
                            )
                        },
                        singleLine = true,
                        modifier = Modifier
                            .width(96.dp),
                        textStyle = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.End, color = hintColor),
                        //interactionSource = interactionSource,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        keyboardActions = KeyboardActions(onDone  = {}),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            //containerColor = MaterialTheme.colorScheme.background,
                            cursorColor = MaterialTheme.colorScheme.primary
                        )
                    )
                    Text(
                        text = " g",
                        modifier = Modifier
                            .padding(top = 24.dp, end = 16.dp)
                            .width(36.dp),
                        textAlign = TextAlign.End
                    )
                }
            }

            HorizontalDivider(
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp)
            )

            Row(
                modifier = Modifier
                    .padding(bottom = 4.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = "Carbohydrates",
                    modifier = Modifier.padding(top = 24.dp, start = 16.dp),
                    textAlign = TextAlign.Start
                )
                //Spacer(modifier = Modifier.size(128.dp))
                Row{
                    TextField(
                        value = ingredientDetails.carbohydrates,
                        onValueChange = { onValueChange(ingredientDetails.copy(carbohydrates = it)) },
                        label = {
                            Text(
                                text = ""
                            )
                        },
                        singleLine = true,
                        modifier = Modifier
                            .width(96.dp),
                        textStyle = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.End, color = hintColor),
                        //interactionSource = interactionSource,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        keyboardActions = KeyboardActions(onDone  = {}),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            //containerColor = MaterialTheme.colorScheme.background,
                            cursorColor = MaterialTheme.colorScheme.primary
                        )
                    )
                    Text(
                        text = " g",
                        modifier = Modifier
                            .padding(top = 24.dp, end = 16.dp)
                            .width(36.dp),
                        textAlign = TextAlign.End
                    )
                }
            }
//        HorizontalDivider(
//            thickness = 2.dp,
//            color = MaterialTheme.colorScheme.onSurface,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(start = 8.dp, end = 8.dp)
//        )
        }

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