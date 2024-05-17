package com.yanetto.netto.ui.listOfIngredientsScreen

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yanetto.netto.R
import com.yanetto.netto.ui.theme.NettoTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import com.yanetto.netto.model.Ingredient
import kotlinx.coroutines.launch

@Composable
fun ListOfIngredientsScreen(
    modifier: Modifier = Modifier,
    navigateToIngredientEntry: () -> Unit = {},
    navigateToIngredientUpdate: (Int) -> Unit = {},
    viewModel: ListOfIngredientsViewModel = viewModel(factory = ListOfIngredientsViewModel.Factory)
){
    val listOfIngredientsUiState by viewModel.ingredientsUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Column (
        modifier = modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp)
    ){
        SearchBar(navigateToIngredientEntry = navigateToIngredientEntry)

        IngredientList(ingredientList = listOfIngredientsUiState.ingredientList, onIngredientCardClicked = navigateToIngredientUpdate, onDeleteClicked = { coroutineScope.launch { viewModel.deleteIngredient(it) } }, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    navigateToIngredientEntry: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(start = 8.dp)
    ) {
        OutlinedCard(
            shape = ButtonDefaults.shape,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .weight(1f)
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.search_black_36dp),
                    contentDescription = null,
                    modifier = Modifier.padding(vertical = 8.dp),
                    tint = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.size(8.dp))

                val focusManager = LocalFocusManager.current
                var textValue by remember{ mutableStateOf(TextFieldValue("")) }
                val interactionSource = remember{ MutableInteractionSource() }
                val isFocused = interactionSource.collectIsFocusedAsState()
                val valueHint = stringResource(id = R.string.search_your_ingredients)

                LaunchedEffect(isFocused.value){
                    if(!isFocused.value) textValue = TextFieldValue("")
                }

                val color = MaterialTheme.colorScheme.onSurface
                val hintColor = if(isFocused.value) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f) else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)

                BasicTextField(
                    value = textValue,
                    onValueChange = { textValue = it },
                    singleLine = true,
                    textStyle = MaterialTheme.typography.headlineSmall.copy(textAlign = TextAlign.Start, color = color),
                    interactionSource = interactionSource,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    keyboardActions = KeyboardActions(onDone  = {
                        focusManager.clearFocus()
                    }),
                    cursorBrush = SolidColor(color)
                ){
                    Box(
                        modifier = Modifier
                    ){
                        if(textValue.text.isEmpty()){
                            Text(
                                text = valueHint,
                                style = MaterialTheme.typography.headlineSmall,
                                color = hintColor,
                                modifier = Modifier.align(Alignment.CenterStart)
                            )
                        }
                        it()
                    }
                }
            }
        }
        IconButton(onClick = { navigateToIngredientEntry() }, modifier = Modifier
            .size(54.dp)
            .align(Alignment.CenterVertically)) {
            Icon(
                painter = painterResource(id = R.drawable.add_40dp_fill0_wght400_grad0_opsz40),
                contentDescription = null,
                modifier = Modifier.padding(vertical = 8.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun IngredientCard(
    ingredient: Ingredient,
    modifier: Modifier = Modifier,
    onIngredientCardClicked: (Int) -> Unit,
    onDeleteClicked: (Int) -> Unit
){
    Box(
        modifier = modifier
            .clickable(onClick = { onIngredientCardClicked(ingredient.id) })
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
            IconButton(onClick = { onDeleteClicked(ingredient.id) }) {
                Icon(
                    painter = painterResource(id = R.drawable.delete_40dp_fill0_wght400_grad0_opsz40),
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
    HorizontalDivider(Modifier.padding(8.dp), thickness = 2.dp)
}

@Composable
fun IngredientList(
    ingredientList: List<Ingredient>,
    onIngredientCardClicked: (Int) -> Unit,
    onDeleteClicked: (Int) -> Unit,
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
                onIngredientCardClicked = onIngredientCardClicked,
                onDeleteClicked = onDeleteClicked
            )
        }
    }
}


@Preview
@Composable
fun ListOfIngredientsScreenPreview(){
    NettoTheme {
        Surface (
            modifier = Modifier.fillMaxSize()
        ){
            ListOfIngredientsScreen()
        }
    }
}