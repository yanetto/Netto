package com.yanetto.netto.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.yanetto.netto.R
import com.yanetto.netto.ui.recipeScreen.RecipeScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yanetto.netto.ui.ingredientScreen.IngredientScreen
import com.yanetto.netto.ui.listOfIngredientsScreen.ListOfIngredientsScreen
import com.yanetto.netto.ui.listOfRecipesScreen.ListOfRecipesScreen
import com.yanetto.netto.ui.profileScreen.ProfileScreen
import com.yanetto.netto.ui.recipeScreen.RecipeViewModel

enum class NettoScreen(){
    RecipeScreen,
    EditRecipeScreen,
    IngredientScreen,
    ListOfRecipesScreen,
    ListOfIngredientsScreen,
    ProfileScreen
}

@Composable
fun NettoAppBar(
    currentScreen: NettoScreen,
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    BottomAppBar(
        modifier = Modifier.height(80.dp),
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        content = {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ){
                IconButton(
                    onClick = { if (currentScreen != NettoScreen.ListOfRecipesScreen) navController.navigate(NettoScreen.ListOfRecipesScreen.name)},
                    modifier = Modifier.padding(8.dp).size(48.dp)
                ) {
                        Icon(
                            painter = painterResource(id = R.drawable.menu_book_40dp_fill0_wght400_grad0_opsz40),
                            contentDescription = null,
                            modifier = Modifier.padding(8.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        //Text(text = "Recipes", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.bodyMedium)
                }

                IconButton(
                    onClick = { if (currentScreen != NettoScreen.ListOfIngredientsScreen) navController.navigate(NettoScreen.ListOfIngredientsScreen.name)},
                    modifier = Modifier.padding(8.dp).size(48.dp)
                ) {
                        Icon(
                            painter = painterResource(id = R.drawable.grocery_40dp_fill0_wght400_grad0_opsz40),
                            contentDescription = null,
                            modifier = Modifier.padding(8.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        //Text(text = "Ingredients", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.bodyMedium)
                }

                IconButton(
                    onClick = { if (currentScreen != NettoScreen.ProfileScreen) navController.navigate(NettoScreen.ProfileScreen.name)},
                    modifier = Modifier.padding(8.dp).size(48.dp)
                ){
                        Icon(
                            painter = painterResource(id = R.drawable.person_40dp_fill0_wght400_grad0_opsz40),
                            contentDescription = null,
                            modifier = Modifier.padding(8.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        //Text(text = "Profile", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.bodyMedium)
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
    var currentScreen by remember { mutableStateOf(NettoScreen.RecipeScreen)}
    Scaffold(
        bottomBar = {
            NettoAppBar(
                navController = navController,
                currentScreen = currentScreen
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = NettoScreen.ListOfRecipesScreen.name,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(route = NettoScreen.RecipeScreen.name){
                RecipeScreen()
                currentScreen = NettoScreen.RecipeScreen
            }

            composable(route = NettoScreen.ListOfRecipesScreen.name){
                ListOfRecipesScreen(
                    onRecipeCardClicked = {
                        viewModel.setCurrentRecipe(it)
                        navController.navigate(NettoScreen.RecipeScreen.name)
                    }
                )
                currentScreen = NettoScreen.ListOfRecipesScreen
            }

            composable(route = NettoScreen.ListOfIngredientsScreen.name){
                ListOfIngredientsScreen(onIngredientCardClicked = {
                    navController.navigate(NettoScreen.IngredientScreen.name)
                })
                currentScreen = NettoScreen.ListOfIngredientsScreen
            }

            composable(route = NettoScreen.IngredientScreen.name){
                IngredientScreen()
                currentScreen = NettoScreen.IngredientScreen
            }

            composable(route = NettoScreen.ProfileScreen.name){
                ProfileScreen()
                currentScreen = NettoScreen.ProfileScreen
            }
        }
    }
}