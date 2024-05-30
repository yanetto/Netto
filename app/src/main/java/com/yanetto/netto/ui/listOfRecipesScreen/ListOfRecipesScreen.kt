package com.yanetto.netto.ui.listOfRecipesScreen

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yanetto.netto.R
import com.yanetto.netto.model.Recipe
import com.yanetto.netto.ui.theme.NettoTheme
import kotlinx.coroutines.launch

@Composable
fun ListOfRecipesScreen(
    modifier: Modifier = Modifier,
    onRecipeCardClicked: (Int) -> Unit,
    navigateToRecipeEntry: () -> Unit = {},
    listOfRecipesViewModel: ListOfRecipesViewModel = viewModel(factory = ListOfRecipesViewModel.Factory)
){
    val listOfRecipesUiState by listOfRecipesViewModel.recipeUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val query by listOfRecipesViewModel.query.collectAsState()

    Column (
        modifier = modifier
            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
            .fillMaxHeight()
    ){
        SearchBar(navigateToRecipeEntry = navigateToRecipeEntry, onValueChange = listOfRecipesViewModel::onQueryChange, query = query)

        RecipeList(recipeList = listOfRecipesUiState.recipeList, onRecipeCardClicked = onRecipeCardClicked, modifier = Modifier.fillMaxWidth(), onDeleteClicked = { coroutineScope.launch { listOfRecipesViewModel.deleteRecipe(it) } })
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    query: String,
    navigateToRecipeEntry: () -> Unit,
    onValueChange: (String) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 8.dp)
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
                var textValue by remember{ mutableStateOf(TextFieldValue(query)) }
                val interactionSource = remember{ MutableInteractionSource() }
                val isFocused = interactionSource.collectIsFocusedAsState()
                val valueHint = stringResource(id = R.string.search_your_recipes)

                LaunchedEffect(isFocused.value){
                    if(!isFocused.value) textValue = TextFieldValue(textValue.text)
                }

                val color = MaterialTheme.colorScheme.onSurface
                val hintColor = if(isFocused.value) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f) else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)

                BasicTextField(
                    value = textValue,
                    onValueChange = {
                        textValue = it
                        onValueChange(textValue.text) },
                    modifier = Modifier.weight(1f),
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
                if (query != ""){
                    IconButton(onClick = {
                        textValue = TextFieldValue("")
                        onValueChange("") },
                        modifier = Modifier
                        .size(54.dp)
                        .align(Alignment.CenterVertically))
                    {
                        Icon(
                            painter = painterResource(id = R.drawable.close_40dp_fill0_wght400_grad0_opsz40),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(vertical = 8.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
        IconButton(onClick = { navigateToRecipeEntry() }, modifier = Modifier
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
fun RecipeCard(
    recipe: Recipe,
    modifier: Modifier = Modifier,
    onRecipeCardClicked: (Int) -> Unit,
    onDeleteClicked: (Int) -> Unit
){
    Box(
        modifier = modifier
            .clickable(onClick = { onRecipeCardClicked(recipe.id) })
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
    ){
        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = recipe.name,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Text(
                    text = recipe.description,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
            IconButton(onClick = {onDeleteClicked(recipe.id)}) {
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
fun RecipeList(
    recipeList: List<Recipe>,
    onRecipeCardClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClicked: (Int) -> Unit
){
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .scrollable(
                orientation = Orientation.Vertical,
                state = rememberScrollState()
            )
    ){
        items(recipeList){ recipe ->
            RecipeCard(
                recipe = recipe,
                modifier = Modifier,
                onRecipeCardClicked = onRecipeCardClicked,
                onDeleteClicked = onDeleteClicked
            )
        }
    }
}


@Preview
@Composable
fun ListOfRecipesScreenPreview(){
    NettoTheme {
        Surface (
            modifier = Modifier.fillMaxSize()
        ){
            ListOfRecipesScreen(onRecipeCardClicked = {})
        }
    }
}