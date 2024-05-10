package com.yanetto.netto.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.yanetto.netto.R
import com.yanetto.netto.ui.recipeScreen.RecipeScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yanetto.netto.ui.ingredientScreen.IngredientScreen
import com.yanetto.netto.ui.listOfIngredientsScreen.ListOfIngredientsScreen
import com.yanetto.netto.ui.listOfRecipesScreen.ListOfRecipesScreen
import com.yanetto.netto.ui.recipeScreen.RecipeViewModel

enum class NettoScreen(){
    RecipeScreen,
    EditRecipeScreen,
    IngredientScreen,
    EditIngredientScreen,
    ListOfRecipesScreen,
    ListOfIngredientsScreen
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NettoAppBar(
    currentScreen: NettoScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
){
    TopAppBar(
        title = {Text(text="")},
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back_black_36dp),
                        contentDescription = null,
                        modifier = Modifier,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NettoApp(
    viewModel: RecipeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navController: NavHostController = rememberNavController()
){
    Scaffold(
//        topBar = {
//            NettoAppBar(
//                canNavigateBack = false,
//                navigateUp = { /* TODO: implement back navigation */ },
//                currentScreen = NettoScreen.RecipeScreen
//            )
//        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = NettoScreen.ListOfIngredientsScreen.name,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(route = NettoScreen.RecipeScreen.name){
                RecipeScreen()
            }

            composable(route = NettoScreen.ListOfRecipesScreen.name){
                ListOfRecipesScreen(
                    onRecipeCardClicked = {
                        viewModel.setCurrentRecipe(it)
                        navController.navigate(NettoScreen.RecipeScreen.name)
                    }
                )
            }

            composable(route = NettoScreen.ListOfIngredientsScreen.name){
                ListOfIngredientsScreen(onIngredientCardClicked = {
                    navController.navigate(NettoScreen.IngredientScreen.name)
                })
            }

            composable(route = NettoScreen.IngredientScreen.name){
                IngredientScreen()
            }
        }
    }
}