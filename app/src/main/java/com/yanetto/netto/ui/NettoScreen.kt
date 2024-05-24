package com.yanetto.netto.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yanetto.netto.R
import com.yanetto.netto.ui.editRecipeScreen.EditRecipeDetailsDestination
import com.yanetto.netto.ui.editRecipeScreen.EditRecipeScreen
import com.yanetto.netto.ui.ingredientScreen.IngredientDetailsDestination
import com.yanetto.netto.ui.ingredientScreen.IngredientScreen
import com.yanetto.netto.ui.listOfIngredientsScreen.ListOfIngredientsScreen
import com.yanetto.netto.ui.listOfRecipesScreen.ListOfRecipesScreen
import com.yanetto.netto.ui.profileScreen.ProfileScreen
import com.yanetto.netto.ui.recipeScreen.RecipeDetailsDestination
import com.yanetto.netto.ui.recipeScreen.RecipeScreen
import com.yanetto.netto.ui.theme.NettoTheme

enum class NettoScreen {
    RecipeScreen,
    EditRecipeScreen,
    IngredientScreen,
    ListOfRecipesScreen,
    ListOfIngredientsScreen,
    ProfileScreen
}

@Composable
fun BottomNavigationBarItem(
    modifier: Modifier = Modifier,
    painter: Painter,
    contentDescription: String?,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .indication(interactionSource = interactionSource, indication = null)
            .clickable(onClick = onClick)
            .fillMaxSize()
    ) {
        Icon(
            painter = painter,
            contentDescription = contentDescription,
            modifier = Modifier.size(32.dp),
            tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun BottomNavigationBar(
    currentScreen: NettoScreen,
    navController: NavController
) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                BottomNavigationBarItem(
                    modifier = Modifier.weight(1f),
                    painter = painterResource(id = R.drawable.menu_book_40dp_fill0_wght400_grad0_opsz40),
                    contentDescription = "Recipes",
                    isSelected = currentScreen == NettoScreen.ListOfRecipesScreen,
                    onClick = { if (currentScreen != NettoScreen.ListOfRecipesScreen) navController.navigate(NettoScreen.ListOfRecipesScreen.name) }
                )
                BottomNavigationBarItem(
                    modifier = Modifier.weight(1f),
                    painter = painterResource(id = R.drawable.grocery_40dp_fill0_wght400_grad0_opsz40),
                    contentDescription = "Ingredients",
                    isSelected = currentScreen == NettoScreen.ListOfIngredientsScreen,
                    onClick = { if (currentScreen != NettoScreen.ListOfIngredientsScreen) navController.navigate(NettoScreen.ListOfIngredientsScreen.name) }
                )
                BottomNavigationBarItem(
                    modifier = Modifier.weight(1f),
                    painter = painterResource(id = R.drawable.person_40dp_fill0_wght400_grad0_opsz40),
                    contentDescription = "Profile",
                    isSelected = currentScreen == NettoScreen.ProfileScreen,
                    onClick = { if (currentScreen != NettoScreen.ProfileScreen) navController.navigate(NettoScreen.ProfileScreen.name) }
                )
            }
        }
    )
}

@Composable
fun NettoApp(
    navController: NavHostController = rememberNavController()
){
    var currentScreen by remember { mutableStateOf(NettoScreen.RecipeScreen)}
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                currentScreen = currentScreen
            )
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = NettoScreen.ListOfRecipesScreen.name,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(
                route = NettoScreen.RecipeScreen.name,
                exitTransition = { fadeOut() },
                enterTransition = { fadeIn() }
            ){
                RecipeScreen(
                    navigateToEditRecipe = { navController.navigate("${NettoScreen.EditRecipeScreen.name}/$it") }
                )
                currentScreen = NettoScreen.RecipeScreen
            }

            composable(
                route = RecipeDetailsDestination.routeWithArgs,
                exitTransition = { fadeOut() },
                enterTransition = { fadeIn() },
                arguments = listOf(navArgument(RecipeDetailsDestination.recipeIdArg) {
                    type = NavType.IntType
                })
            ) {
                RecipeScreen(
                    navigateToEditRecipe = {
                        navController.navigate("${NettoScreen.EditRecipeScreen.name}/$it")
                    }
                )
                currentScreen = NettoScreen.RecipeScreen
            }

            composable(
                route = NettoScreen.EditRecipeScreen.name,
                exitTransition = { fadeOut() },
                enterTransition = { fadeIn() }
            ){
                EditRecipeScreen(
                    navigateBack = { navController.navigateUp()
                    }
                )
                currentScreen = NettoScreen.EditRecipeScreen
            }

            composable(
                route = EditRecipeDetailsDestination.routeWithArgs,
                exitTransition = { fadeOut() },
                enterTransition = { fadeIn() },
                arguments = listOf(navArgument(EditRecipeDetailsDestination.recipeIdArg) {
                    type = NavType.IntType
                })
            ) {
                EditRecipeScreen(
                    navigateBack = { navController.navigateUp() }
                )
                currentScreen = NettoScreen.EditRecipeScreen
            }

            composable(
                route = NettoScreen.ListOfRecipesScreen.name,
                exitTransition = { fadeOut() },
                enterTransition = { fadeIn() }
            ){
                ListOfRecipesScreen(
                    onRecipeCardClicked = {
                        navController.navigate("${NettoScreen.RecipeScreen.name}/$it")
                    },
                    navigateToRecipeEntry = { navController.navigate(NettoScreen.EditRecipeScreen.name) }
                )
                currentScreen = NettoScreen.ListOfRecipesScreen
            }

            composable(
                route = NettoScreen.ListOfIngredientsScreen.name,
                exitTransition = { fadeOut() },
                enterTransition = { fadeIn() }
            ){
                    ListOfIngredientsScreen(
                        navigateToIngredientUpdate = {
                            navController.navigate("${NettoScreen.IngredientScreen.name}/$it") },
                        navigateToIngredientEntry = {navController.navigate(NettoScreen.IngredientScreen.name)}
                    )
                    currentScreen = NettoScreen.ListOfIngredientsScreen
            }

            composable(
                route = IngredientDetailsDestination.routeWithArgs,
                exitTransition = { fadeOut() },
                enterTransition = { fadeIn() },
                arguments = listOf(navArgument(IngredientDetailsDestination.ingredientIdArg) {
                    type = NavType.IntType
                })
            ) {
                IngredientScreen(
                    navigateBack = { navController.navigateUp() }
                )
                currentScreen = NettoScreen.IngredientScreen
            }

            composable(
                route = NettoScreen.IngredientScreen.name,
                exitTransition = { fadeOut() },
                enterTransition = { fadeIn() }
            ){
                IngredientScreen(navigateBack = { navController.navigateUp() })
                currentScreen = NettoScreen.IngredientScreen
            }

            composable(
                route = NettoScreen.ProfileScreen.name,
                exitTransition = { fadeOut() },
                enterTransition = { fadeIn() },
                popEnterTransition = { fadeIn(animationSpec = tween(800)) },
                popExitTransition = { fadeOut() }
            ){
                ProfileScreen()
                currentScreen = NettoScreen.ProfileScreen
            }
        }
    }
}

@Preview
@Composable
fun ListOfRecipesScreenPreview(){
    NettoTheme {
        Surface (
            modifier = Modifier.fillMaxWidth()
        ){
            BottomNavigationBar(currentScreen = NettoScreen.ListOfRecipesScreen, navController = rememberNavController())
        }
    }
}