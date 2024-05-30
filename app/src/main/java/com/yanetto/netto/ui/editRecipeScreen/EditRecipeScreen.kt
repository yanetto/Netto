package com.yanetto.netto.ui.editRecipeScreen

import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yanetto.netto.R
import com.yanetto.netto.model.Ingredient
import kotlinx.coroutines.launch
import java.math.RoundingMode


object EditRecipeDetailsDestination {
    const val route = "EditRecipeScreen"
    const val recipeIdArg = "recipeId"
    const val routeWithArgs = "$route/{$recipeIdArg}"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditRecipeScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit = {},
    editRecipeViewModel: EditRecipeViewModel = viewModel(factory = EditRecipeViewModel.Factory)
){
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val coroutineScope = rememberCoroutineScope()
    val editRecipeUiState by editRecipeViewModel.recipeUiState.collectAsState()

    Column {
        Row (modifier = modifier
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
                onClick = {
                    coroutineScope.launch { editRecipeViewModel.saveItem() }
                        navigateBack()},
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
                Spacer(modifier = Modifier.height(224.dp))
                ElevatedCard (
                    modifier = Modifier.fillMaxHeight(),
                    shape = RoundedCornerShape(8,8, 0, 0),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 12.dp)
                ){
                    RecipeNameLabel(
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
                        recipeDetails = editRecipeUiState.recipeDetails,
                        onValueChange = editRecipeViewModel::updateUiState
                    )

                    HorizontalDivider(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                            .fillMaxWidth()
                    )

                    RecipeDescriptionLabel(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        recipeDetails = editRecipeUiState.recipeDetails,
                        onValueChange = editRecipeViewModel::updateUiState
                    )

                    ServingsLabel(
                        modifier = Modifier.padding(8.dp),
                        recipeDetails = editRecipeUiState.recipeDetails,
                        onValueChange = editRecipeViewModel::updateUiState
                    )

                    Row(
                        modifier = modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 8.dp),
                            text = stringResource(id = R.string.ingredients),
                            style = MaterialTheme.typography.headlineMedium,
                            textAlign = TextAlign.Start
                        )
                        IconButton(
                            onClick = { openBottomSheet = !openBottomSheet },
                            modifier = Modifier
                                .size(36.dp)
                                .align(Alignment.CenterVertically)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.add_40dp_fill0_wght400_grad0_opsz40),
                                contentDescription = null,
                                modifier = Modifier,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    HorizontalDivider(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                            .fillMaxWidth()
                    )

                    for (i in editRecipeUiState.listOfIngredients.indices) {
                        val ingredient = editRecipeUiState.listOfIngredients[i].ingredient
                        IngredientItem(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            ingredient = ingredient,
                            weight = editRecipeUiState.listOfIngredients[i].ingredientWeight,
                            onChangeIngredientWeight = editRecipeViewModel::updateIngredientWeight
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    if (openBottomSheet){
        ModalBottomSheet(
            onDismissRequest = { openBottomSheet = false },
            modifier = modifier.height(screenHeight * 0.9f),
            sheetState = bottomSheetState
        ){
            IngredientList(
                modifier = modifier.padding(horizontal = 8.dp),
                ingredientList = editRecipeUiState.allIngredientList,
                onIngredientCardClicked = editRecipeViewModel::addIngredientToRecipe
            )
        }
    }
}

@Composable
fun IngredientList(
    ingredientList: List<Ingredient>,
    onIngredientCardClicked: (Ingredient, Float) -> Unit,
    modifier: Modifier = Modifier
){
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .scrollable(
                orientation = Orientation.Vertical,
                state = rememberScrollState()
            )
    ){
        items(ingredientList){ingredient ->
            IngredientCard(
                ingredient = ingredient,
                modifier = Modifier,
                onIngredientCardClicked = onIngredientCardClicked
            )
        }
    }
}

@Composable
fun IngredientCard(
    ingredient: Ingredient,
    modifier: Modifier = Modifier,
    onIngredientCardClicked: (Ingredient, Float) -> Unit
){
    Box(
        modifier = modifier
            .clickable(onClick = { onIngredientCardClicked(ingredient, 100f) })
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
    ){
        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Column (modifier = Modifier.padding(8.dp)){
                Text(
                    text = ingredient.name,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Text(
                    text = ingredient.manufacturer,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    }
    HorizontalDivider(Modifier.padding(8.dp), thickness = 2.dp)
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
fun ServingsLabel(
    modifier: Modifier = Modifier,
    recipeDetails: RecipeDetails,
    onValueChange: (RecipeDetails) -> Unit = {}
){
    val focusManager = LocalFocusManager.current
    var textValue by remember{ mutableStateOf(TextFieldValue("")) }
    val interactionSource = remember{ MutableInteractionSource() }
    val isFocused = interactionSource.collectIsFocusedAsState()
    val valueHint = if (recipeDetails.servingsCount == "1") recipeDetails.servingsCount + " Serving" else recipeDetails.servingsCount + " Servings"

    LaunchedEffect(isFocused.value){
        if(!isFocused.value) textValue = TextFieldValue("")
    }

    val color = MaterialTheme.colorScheme.onSurface
    val hintColor = if(isFocused.value) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f) else MaterialTheme.colorScheme.onSurface

    BasicTextField(
        value = textValue,
        onValueChange = {
            textValue = it
            onValueChange(recipeDetails.copy(servingsCount = it.text)) },
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        textStyle = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Start, color = color),
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
                    style = MaterialTheme.typography.titleLarge,
                    color = hintColor,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
            }
            it()
        }
    }
}

@Composable
fun RecipeNameLabel(
    modifier: Modifier = Modifier,
    recipeDetails: RecipeDetails,
    onValueChange: (RecipeDetails) -> Unit = {}
){
    val focusManager = LocalFocusManager.current
    var textValue by remember{ mutableStateOf(TextFieldValue("")) }
    val interactionSource = remember{ MutableInteractionSource() }
    val isFocused = interactionSource.collectIsFocusedAsState()
    val valueHint = recipeDetails.name

    LaunchedEffect(isFocused.value){
        if(!isFocused.value) textValue = TextFieldValue("")
    }

    val color = MaterialTheme.colorScheme.onSurface
    val hintColor = if(isFocused.value) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f) else MaterialTheme.colorScheme.onSurface

    BasicTextField(
        value = textValue,
        onValueChange = {
            textValue = it
            onValueChange(recipeDetails.copy(name = it.text)) },
        singleLine = true,
        modifier = modifier.padding(8.dp),
        textStyle = MaterialTheme.typography.displaySmall.copy(textAlign = TextAlign.Start, color = color),
        interactionSource = interactionSource,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
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
                    style = MaterialTheme.typography.displaySmall,
                    color = hintColor,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
            }
            it()
        }
    }
}

@Composable
fun RecipeDescriptionLabel(
    modifier: Modifier = Modifier,
    recipeDetails: RecipeDetails,
    onValueChange: (RecipeDetails) -> Unit = {}
){
    val focusManager = LocalFocusManager.current
    var textValue by remember{ mutableStateOf(TextFieldValue("")) }
    val interactionSource = remember{ MutableInteractionSource() }
    val isFocused = interactionSource.collectIsFocusedAsState()
    val valueHint = recipeDetails.description

    LaunchedEffect(isFocused.value){
        if(!isFocused.value) textValue = TextFieldValue("")
    }

    val color = MaterialTheme.colorScheme.onSurface
    val hintColor = if(isFocused.value) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f) else MaterialTheme.colorScheme.onSurface

    BasicTextField(
        value = textValue,
        onValueChange = {
            textValue = it
            onValueChange(recipeDetails.copy(description = it.text)) },
        singleLine = true,
        modifier = modifier.padding(8.dp),
        textStyle = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Start, color = color),
        interactionSource = interactionSource,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
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
                    style = MaterialTheme.typography.titleLarge,
                    color = hintColor,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
            }
            it()
        }
    }
}