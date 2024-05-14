package com.yanetto.netto.ui.listOfIngredientsScreen

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import com.yanetto.netto.model.Ingredient

@Composable
fun ListOfIngredientsScreen(
    modifier: Modifier = Modifier,
    onIngredientCardClicked: (Ingredient) -> Unit,
    viewModel: ListOfIngredientsViewModel = viewModel(factory = ListOfIngredientsViewModel.Factory)
){
    val listOfIngredientsUiState by viewModel.ingredientsUiState.collectAsState()

    Column (
        modifier = modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp)
    ){
        SearchBar()

        IngredientList(ingredientList = listOfIngredientsUiState.ingredientList, onIngredientCardClicked = onIngredientCardClicked, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun SearchBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth()
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

                var textValue by remember{ mutableStateOf(TextFieldValue("")) }

                BasicTextField(
                    value = textValue,
                    onValueChange = {},
                    singleLine = true,
                    textStyle = MaterialTheme.typography.headlineSmall.copy(textAlign = TextAlign.Start, color = MaterialTheme.colorScheme.onSurface),
                    modifier = Modifier
                ){
                    Box(
                        modifier = Modifier
                    ){
                        if(textValue.text.isEmpty()){
                            Text(
                                text = stringResource(R.string.search_your_ingredients),
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
        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(54.dp).align(Alignment.CenterVertically)) {
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
    onIngredientCardClicked: (Ingredient) -> Unit
){
    Box(
        modifier = modifier
            .clickable(onClick = { onIngredientCardClicked(ingredient) })
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
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
    HorizontalDivider(Modifier.padding(8.dp), thickness = 2.dp)
}

@Composable
fun IngredientList(ingredientList: List<Ingredient>, onIngredientCardClicked: (Ingredient) -> Unit, modifier: Modifier = Modifier){
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


@Preview
@Composable
fun ListOfIngredientsScreenPreview(){
    NettoTheme {
        Surface (
            modifier = Modifier.fillMaxSize()
        ){
            ListOfIngredientsScreen(onIngredientCardClicked = {})
        }
    }
}