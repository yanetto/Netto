package com.yanetto.netto.ui.listOfRecipesScreen

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.yanetto.netto.data.Datasource
import com.yanetto.netto.model.Recipe
import com.yanetto.netto.ui.theme.NettoTheme
import androidx.compose.foundation.lazy.items

@Composable
fun ListOfRecipesScreen(
    modifier: Modifier = Modifier,
    onRecipeCardClicked: (Recipe) -> Unit,
    listOfRecipesViewModel: ListOfRecipesViewModel = viewModel()
){
    val recipeUiState by listOfRecipesViewModel.uiState.collectAsState()
    val recipeList = Datasource().loadRecipes()

    Column (
        modifier = modifier.padding(vertical = 16.dp, horizontal = 8.dp)
    ){
        SearchBar()

        RecipeList(recipeList = recipeList, onRecipeCardClicked = onRecipeCardClicked, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun SearchBar(modifier: Modifier = Modifier) {
    OutlinedCard(
        shape = ButtonDefaults.shape,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.search_black_36dp),
                contentDescription = null,
                modifier = Modifier.padding(vertical = 8.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.size(8.dp))

            var textValue by remember{ mutableStateOf(TextFieldValue("")) }

            BasicTextField(
                value = textValue,
                onValueChange = {},
                singleLine = true,
                textStyle = MaterialTheme.typography.headlineSmall.copy(textAlign = TextAlign.Start, color = MaterialTheme.colorScheme.onSurface),
                modifier = Modifier.fillMaxWidth()
            ){
                Box(
                    modifier = Modifier.fillMaxWidth()
                ){
                    if(textValue.text.isEmpty()){
                        Text(
                            text = stringResource(R.string.search_your_recipes),
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                            modifier = Modifier.align(Alignment.CenterStart)
                        )
                    }
                    it()
                }
            }

        }
    }
}

@Composable
fun RecipeCard(
    recipe: Recipe,
    modifier: Modifier = Modifier,
    onRecipeCardClicked: (Recipe) -> Unit
){
    Card(
        modifier = modifier
            .clickable ( onClick = {onRecipeCardClicked(recipe)} )
            .padding(8.dp)
            .fillMaxWidth()
    ){
        Column (modifier = Modifier.padding(8.dp)){
            Text(
                text = recipe.name,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Text(
                text = recipe.description,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

@Composable
fun RecipeList(recipeList: List<Recipe>, onRecipeCardClicked: (Recipe) -> Unit, modifier: Modifier = Modifier){
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .scrollable(
                orientation = Orientation.Vertical,
                state = rememberScrollState()
            )
    ){
        items(recipeList){recipe ->
            RecipeCard(
                recipe = recipe,
                modifier = Modifier,
                onRecipeCardClicked = onRecipeCardClicked
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