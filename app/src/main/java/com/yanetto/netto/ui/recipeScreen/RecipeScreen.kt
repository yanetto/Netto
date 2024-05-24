
package com.yanetto.netto.ui.recipeScreen

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalConfiguration
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
import com.yanetto.netto.model.NutritionalOption
import com.yanetto.netto.ui.theme.NettoTheme
import java.math.RoundingMode

object RecipeDetailsDestination {
    const val route = "RecipeScreen"
    const val recipeIdArg = "recipeId"
    const val routeWithArgs = "$route/{$recipeIdArg}"
}

@Composable
fun RecipeScreen(
    modifier: Modifier = Modifier,
    recipeViewModel: RecipeViewModel = viewModel(factory = RecipeViewModel.Factory),
    navigateToEditRecipe: (Int) -> Unit
){
    val recipeUiState = recipeViewModel.uiState

    LazyColumn (
        modifier = modifier
            .height(LocalConfiguration.current.screenHeightDp.dp)
            .fillMaxWidth()
            .scrollable(
                orientation = Orientation.Vertical,
                state = rememberScrollState()
            )
    ) {
        item {
            Column {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, start = 8.dp, end = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        enabled = true,
                        onClick = { },
                        modifier = Modifier
                            .size(48.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.add_a_photo_24dp),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    IconButton(
                        enabled = true,
                        onClick = { navigateToEditRecipe(recipeUiState.recipeDetails.id) },
                        modifier = Modifier
                            .size(48.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.edit_24dp_fill0_wght400_grad0_opsz24),
                            contentDescription = null,
                            modifier = Modifier,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(224.dp))
            ElevatedCard(
                modifier = Modifier
                    .fillMaxHeight(),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 12.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                NameAndDescriptionLabels(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    recipeName = recipeUiState.recipeDetails.name,
                    recipeDescription = recipeUiState.recipeDetails.description
                )

                ServingsCard(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    servingsCount = recipeUiState.updatedServingsCount,
                    onChangeServingsButtonClick = recipeViewModel::changeServingCount
                )

                Label(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    labelText = stringResource(R.string.ingredients)
                )

                HorizontalDivider(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .width(1.dp)
                )

                for (i in recipeUiState.listOfIngredients.indices) {
                    val ingredient = recipeUiState.listOfIngredients[i]
                    IngredientItem(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        ingredient = ingredient.ingredient,
                        weight = recipeViewModel.getIngredientWeight(i, newTotalWeight = recipeUiState.updatedWeight),
                        onChangeIngredientWeight = recipeViewModel::onChangeIngredientWeight
                    )
                }

                Label(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    labelText = stringResource(R.string.nutritional_info)
                )

                NutritionalInfoSwitch(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    uiState = recipeUiState,
                    onChangeNutritionalOption = recipeViewModel::changeNutritionalOption
                )

                NutritionalInfo(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    energy = recipeViewModel.energy * recipeUiState.newWeight / 100,
                    protein = recipeViewModel.protein * recipeUiState.newWeight / 100,
                    fat = recipeViewModel.fat * recipeUiState.newWeight / 100,
                    carbohydrates = recipeViewModel.carbohydrates * recipeUiState.newWeight / 100
                )

                Spacer(modifier = Modifier.padding(4.dp))

                val price =
                    recipeViewModel.totalPrice * recipeUiState.newWeight / recipeViewModel.totalWeight

                PriceAndWeightLabels(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    label = stringResource(R.string.weight),
                    number = stringResource(R.string.new_weight_g, recipeUiState.newWeight),
                )
                PriceAndWeightLabels(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    label = stringResource(R.string.price),
                    number = stringResource(id = R.string.price_value, price),
                )
                Spacer(
                    modifier = modifier
                        .height(16.dp)
                )
            }
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
                text = stringResource(R.string.str_serving),
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
        modifier = modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp)
    )
}

@Composable
fun NameAndDescriptionLabels(
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

    HorizontalDivider(
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
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .width(1.dp)
            .padding(horizontal = 16.dp)
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

    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .width(1.dp)
            .padding(horizontal = 16.dp)
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
            .padding(vertical = 4.dp, horizontal = 8.dp),
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
    ingredient: Ingredient,
    weight: Float,
    onChangeIngredientWeight: (Ingredient, Float) -> Unit
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
    HorizontalDivider(
        modifier = modifier
            .padding(start = 8.dp, end = 8.dp)
            .fillMaxWidth()
            .width(1.dp)
    )
}

@Composable
fun ServingsCard(
    modifier: Modifier = Modifier,
    servingsCount: Float,
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
                enabled = servingsCount > 0,
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
                text = if (servingsCount == servingsCount.toInt().toFloat()) stringResource(id = R.string.serving, servingsCount) else stringResource(id = R.string.servings, servingsCount),
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
            RecipeScreen(navigateToEditRecipe = {})
        }
    }
}