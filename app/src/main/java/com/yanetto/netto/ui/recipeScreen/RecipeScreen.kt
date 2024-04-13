@file:OptIn(ExperimentalMaterial3Api::class)

package com.yanetto.netto.ui.recipeScreen

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
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
import com.yanetto.netto.model.IngredientInRecipe
import com.yanetto.netto.model.NutritionalOption
import com.yanetto.netto.ui.theme.NettoTheme
import java.math.RoundingMode


@Composable
fun RecipeScreen(
    modifier: Modifier = Modifier,
    recipeViewModel: RecipeViewModel = viewModel()
){
    val recipeUiState by recipeViewModel.uiState.collectAsState()

    LazyColumn (
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp)
            .scrollable(
                orientation = Orientation.Vertical,
                state = rememberScrollState()
            )
    ) {
        item{
            NameAndDescription(
                recipeName = recipeUiState.currentRecipe.name,
                recipeDescription = recipeUiState.currentRecipe.description
            )

            ServingsCard(
                servingsCount = recipeUiState.updatedServingsCount,
                onChangeServingsButtonClick = recipeViewModel::changeServingCount
            )

            Label(
                labelText = stringResource(R.string.ingredients)
            )

            Divider(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .fillMaxWidth()
                    .width(1.dp)
            )
        }

        items(recipeUiState.currentRecipe.ingredientList.size) {
            val ingredient = recipeUiState.currentRecipe.ingredientList[it]
            IngredientItem(
                ingredient = ingredient,
                weight = recipeUiState.currentRecipe.getIngredientWeight(it, newTotalWeight = recipeUiState.updatedWeight),
                onChangeIngredientWeight = recipeViewModel::onChangeIngredientWeight
            )
        }

        item{
            Label(
                labelText = stringResource(R.string.nutritional_info)
            )

            NutritionalInfoSwitch(uiState = recipeUiState, onChangeNutritionalOption = recipeViewModel::changeNutritionalOption)

            NutritionalInfo(
                energy = recipeUiState.currentRecipe.energy * recipeUiState.newWeight / 100,
                protein = recipeUiState.currentRecipe.protein * recipeUiState.newWeight / 100,
                fat = recipeUiState.currentRecipe.fat * recipeUiState.newWeight / 100,
                carbohydrates = recipeUiState.currentRecipe.carbohydrates * recipeUiState.newWeight / 100
            )

            Spacer(modifier = Modifier.padding(4.dp))
            val price = recipeUiState.currentRecipe.totalPrice * recipeUiState.newWeight/recipeUiState.currentRecipe.totalWeight
            PriceAndWeightLabels(
                label = stringResource(R.string.price),
                number = stringResource(id = R.string.price_value, price),
            )
            PriceAndWeightLabels(
                label = stringResource(R.string.weight),
                number = "${recipeUiState.newWeight} g",
            )
            Spacer(
                modifier = modifier
                    .height(16.dp)
            )
        }

    }
}

@Composable
fun NutritionalInfoSwitch(
    uiState: RecipeUiState,
    modifier: Modifier = Modifier,
    onChangeNutritionalOption: (NutritionalOption) -> Unit
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
            enabled = uiState.selectedNutritionalOption != NutritionalOption.HUNDRED_GRAMS,
            onClick = { onChangeNutritionalOption(NutritionalOption.HUNDRED_GRAMS) },
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
            enabled = uiState.selectedNutritionalOption != NutritionalOption.SERVING,
            onClick = { onChangeNutritionalOption(NutritionalOption.SERVING) },
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
            enabled = uiState.selectedNutritionalOption != NutritionalOption.TOTAL,
            onClick = { onChangeNutritionalOption(NutritionalOption.TOTAL) },
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
fun NameAndDescription(
    modifier: Modifier = Modifier,
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
    modifier: Modifier = Modifier,
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

    NutritionalItem(modifier = modifier, param = stringResource(R.string.energy), info = stringResource(R.string.kcal, energy))
    NutritionalItem(modifier = modifier, param = stringResource(R.string.protein), info = stringResource(R.string.g_value, protein))
    NutritionalItem(modifier = modifier, param = stringResource(R.string.fat), info = stringResource(R.string.g_value, fat))
    NutritionalItem(modifier = modifier, param = stringResource(R.string.carbohydrates), info = stringResource(R.string.g_value, carbohydrates))
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
    modifier: Modifier = Modifier,
    ingredient: IngredientInRecipe,
    weight: Float,
    onChangeIngredientWeight: (IngredientInRecipe, Float) -> Unit
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
                text = ingredient.ingredient.name,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = ingredient.ingredient.manufacturer,
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelLarge
            )
        }

        Spacer(modifier = Modifier.size(16.dp))

        var textValue by remember{mutableStateOf(TextFieldValue(""))}
        val interactionSource = remember{ MutableInteractionSource() }
        val isFocused = interactionSource.collectIsFocusedAsState()
        val weightHint = weight.toBigDecimal().setScale(1, RoundingMode.UP).toFloat().toString()

        LaunchedEffect(isFocused.value){
            if(!isFocused.value) textValue = TextFieldValue("")
        }

        val color = if(textValue.text.toFloatOrNull() != null || textValue.text.isEmpty()) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.errorContainer
        val hintColor = if(isFocused.value) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f) else MaterialTheme.colorScheme.onSurface

        BasicTextField(
            value = textValue,
            onValueChange = {textValue = it},
            singleLine = true,
            modifier = Modifier
                .weight(1f),
            textStyle = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.End, color = color),
            interactionSource = interactionSource,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            keyboardActions = KeyboardActions(onDone  = {
                onChangeIngredientWeight(ingredient, textValue.text.toFloat())
                focusManager.clearFocus()
            }),
            cursorBrush = SolidColor(color)
        ){
            Box(
                modifier = Modifier.fillMaxWidth()
            ){
                if(textValue.text.isEmpty()){
                    Text(
                        text = weightHint,
                        style = MaterialTheme.typography.titleMedium,
                        color = hintColor,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }
                it()
            }
        }

        Text(
            text = stringResource(R.string.g),
            style = MaterialTheme.typography.titleMedium,
            color = color
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
    modifier: Modifier = Modifier,
    servingsCount: Int,
    onChangeServingsButtonClick: (Boolean) -> Unit
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
            IconButton(
                enabled = servingsCount > 1,
                onClick = { onChangeServingsButtonClick(false) },
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
                text = stringResource(R.string.servings, servingsCount),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .weight(4f, true)
            )

            IconButton(
                onClick = { onChangeServingsButtonClick(true) },
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