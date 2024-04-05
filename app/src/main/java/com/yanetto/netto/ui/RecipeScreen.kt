@file:OptIn(ExperimentalMaterial3Api::class)

package com.yanetto.netto.ui

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yanetto.netto.R
import com.yanetto.netto.model.Ingredient
import com.yanetto.netto.ui.theme.NettoTheme

@Composable
fun RecipeScreen(
    modifier: Modifier = Modifier,
    nettoViewModel: NettoViewModel = viewModel()
){
    val nettoUiState by nettoViewModel.uiState.collectAsState()

    LazyColumn (
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .scrollable(
                orientation = Orientation.Vertical,
                state = rememberScrollState()
            )
    ) {
        item{
            NameAndDescription(
                modifier = modifier,
                recipeName = nettoUiState.currentRecipe.name,
                recipeDescription = nettoUiState.currentRecipe.description
            )

            ServingsCard(
                modifier = modifier,
                servingsCount = nettoUiState.currentRecipe.servingsCount
            )

            Label(
                modifier = modifier,
                labelText = stringResource(R.string.ingredients)
            )

            Divider(
                modifier = modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .fillMaxWidth()
                    .width(1.dp)
            )
        }

//        Row(
//            modifier = modifier
//                .padding(8.dp)
//        ) {
//            val colors = ButtonDefaults.outlinedButtonColors(
//                containerColor = MaterialTheme.colorScheme.surface,
//                contentColor = MaterialTheme.colorScheme.onSurface,
//                disabledContainerColor = MaterialTheme.colorScheme.primary,
//                disabledContentColor = MaterialTheme.colorScheme.onPrimary
//            )
//            var isIngredientsSelected by remember { mutableStateOf(true) }
//            OutlinedButton(
//                enabled = !isIngredientsSelected,
//                onClick = { isIngredientsSelected = !isIngredientsSelected },
//                colors = colors,
//                modifier = modifier
//                    .weight(1f, true)
//            ) {
//                Text(
//                    text = stringResource(R.string.ingredients)
//                )
//            }
//            Spacer(modifier = modifier.weight(0.1f, true))
//            OutlinedButton(
//                enabled = isIngredientsSelected,
//                onClick = { isIngredientsSelected = !isIngredientsSelected },
//                colors = colors,
//                modifier = modifier
//                    .weight(1f, true)
//            ) {
//                Text(text = stringResource(R.string.nutritional_info))
//            }
//        }

        items(nettoUiState.currentRecipe.ingredientList) {ingredient ->
            IngredientItem(
                modifier = modifier,
                ingredient = ingredient
            )
        }

        item{
            Label(
                modifier = modifier,
                labelText = stringResource(R.string.nutritional_info)
            )

            NutritionalInfoSwitch(modifier = modifier)

            NutritionalInfo(
                modifier = Modifier,
                energy = nettoUiState.currentRecipe.energy,
                protein = nettoUiState.currentRecipe.protein,
                fat = nettoUiState.currentRecipe.fat,
                carbohydrates = nettoUiState.currentRecipe.carbohydrates
            )

            Spacer(modifier = Modifier.padding(4.dp))
            PriceAndWeightLabels(
                label = stringResource(R.string.price),
                number = "${nettoUiState.currentRecipe.totalPrice} rub",
                modifier = modifier
            )
            PriceAndWeightLabels(
                label = stringResource(R.string.weight),
                number = "${nettoUiState.currentRecipe.totalWeight} g",
                modifier = modifier
            )
        }

    }
}

@Composable
fun NutritionalInfoSwitch(
    modifier: Modifier
){
    Row(
        modifier = modifier.padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
    ) {
        val colors = ButtonDefaults.outlinedButtonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = MaterialTheme.colorScheme.onPrimary
        )
        OutlinedButton(
            onClick = {  },
            colors = colors,
            modifier = Modifier
                .weight(1f, true)
        ) {
            Text(
                text = stringResource(R.string._100_g),
                style = MaterialTheme.typography.titleMedium
            )
        }
        Spacer(modifier = Modifier.weight(0.05f, true))
        OutlinedButton(
            enabled = false,
            onClick = {  },
            modifier = Modifier
                .weight(1f, true),
            colors = colors
        ) {
            Text(
                text = stringResource(R.string.serving),
                style = MaterialTheme.typography.titleMedium
            )
        }
        Spacer(modifier = Modifier.weight(0.05f, true))
        OutlinedButton(
            onClick = {  },
            modifier = Modifier
                .weight(1f, true),
            colors = colors,
        ) {
            Text(
                text = stringResource(R.string.total),
                style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
fun Label(
    modifier: Modifier,
    labelText: String
){
    Text(
        text = labelText,
        style = MaterialTheme.typography.headlineMedium,
        modifier = modifier.padding(8.dp)
    )
}

@Composable
fun NameAndDescription(
    modifier: Modifier,
    recipeName: String,
    recipeDescription: String
){
    Text(
        text = recipeName,
        style = MaterialTheme.typography.displaySmall,
        modifier = modifier
            .padding(8.dp)
    )

    Divider(
        modifier = modifier
            .fillMaxWidth()
            .width(1.dp)
            .padding(8.dp)
    )

    Text(
        text = recipeDescription,
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier
            .padding(8.dp)
    )
}

@Composable
fun NutritionalInfo(
    modifier: Modifier,
    energy: Float,
    protein: Float,
    fat: Float,
    carbohydrates: Float
){
    Divider(modifier = Modifier
        .fillMaxWidth()
        .width(1.dp)
        .padding(start = 8.dp, end = 8.dp)
    )

    NutritionalItem(modifier = modifier, param = stringResource(R.string.energy), info = "$energy kcal")
    NutritionalItem(modifier = modifier, param = stringResource(R.string.protein), info = "$protein g")
    NutritionalItem(modifier = modifier, param = stringResource(R.string.fat), info = "$fat g")
    NutritionalItem(modifier = modifier, param = stringResource(R.string.carbohydrates), info = "$carbohydrates g")
}

@Composable
fun NutritionalItem(
    modifier: Modifier,
    param: String,
    info: String
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = param,
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Clip,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.weight(1f, true))

        Text(
            modifier = Modifier,
            text = info,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.titleMedium
        )
    }

    Divider(modifier = Modifier
        .fillMaxWidth()
        .width(1.dp)
        .padding(start = 8.dp, end = 8.dp)
    )
}


@Composable
fun PriceAndWeightLabels(
    label: String,
    number: String,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier,
            text = label,
            textAlign = TextAlign.Start
        )
        Spacer(modifier = modifier.weight(1f, true))
        Text(
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier,
            text = number,
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun IngredientItem(
    modifier: Modifier,
    ingredient: Ingredient
){
    val focusManager = LocalFocusManager.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(2f)) {
            Text(
                text = ingredient.name,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = ingredient.manufacturer,
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelLarge
            )
        }

        Spacer(modifier = Modifier.size(16.dp))

        var textValue by remember{mutableStateOf(TextFieldValue("25.5"))}

        val color = if(textValue.text.toFloatOrNull() != null) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.error


        BasicTextField(
            value = textValue,
            //colors = TextFieldDefaults.outlinedTextFieldColors(
                //focusedBorderColor = Color.Transparent,
                //unfocusedBorderColor = Color.Transparent
            //),
            onValueChange = {textValue = it},
            singleLine = true,
            modifier = Modifier
                .weight(1f),
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End, color = color),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            keyboardActions = KeyboardActions(onDone  = {focusManager.clearFocus()})
        )
        Text(
            text = "g",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(4.dp)
        )
    }
    Divider(modifier = modifier
        .padding(start = 8.dp, end = 8.dp)
        .fillMaxWidth()
        .width(1.dp)
    )
}

@Composable
fun ServingsCard(
    modifier: Modifier,
    servingsCount: Int
){
    OutlinedCard(
        shape = ButtonDefaults.shape,
        modifier = modifier
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ){
            IconButton(onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(0.dp)
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.remove_circle_black_36dp),
                    contentDescription = null,
                    modifier = Modifier,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Text(
                text = "$servingsCount Servings",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .weight(4f, true)
            )

            IconButton(onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(0.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add_circle_black_36dp),
                    contentDescription = null,
                    modifier = Modifier,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}


@Preview
@Composable
fun RecipeScreenPreview(){
    NettoTheme {
        Surface (
            modifier = Modifier.fillMaxSize()
        ){
            RecipeScreen()
        }
    }
}