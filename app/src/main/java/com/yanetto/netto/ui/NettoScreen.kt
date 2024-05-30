package com.yanetto.netto.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
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
            .clip(CircleShape)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
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
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

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
                    isSelected = currentDestination?.route == NettoScreen.ListOfRecipesScreen.name,
                    onClick = {if (currentDestination?.route != NettoScreen.ListOfRecipesScreen.name) {
                        navController.navigate(NettoScreen.ListOfRecipesScreen.name) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }}
                )
                BottomNavigationBarItem(
                    modifier = Modifier.weight(1f),
                    painter = painterResource(id = R.drawable.grocery_40dp_fill0_wght400_grad0_opsz40),
                    contentDescription = "Ingredients",
                    isSelected = currentDestination?.route == NettoScreen.ListOfIngredientsScreen.name,
                    onClick = { if (currentDestination?.route != NettoScreen.ListOfIngredientsScreen.name) {
                        navController.navigate(NettoScreen.ListOfIngredientsScreen.name) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    } }
                )
                BottomNavigationBarItem(
                    modifier = Modifier.weight(1f),
                    painter = painterResource(id = R.drawable.person_40dp_fill0_wght400_grad0_opsz40),
                    contentDescription = "Profile",
                    isSelected = currentDestination?.route == NettoScreen.ProfileScreen.name,
                    onClick = { if (currentDestination?.route != NettoScreen.ProfileScreen.name) {
                        navController.navigate(NettoScreen.ProfileScreen.name) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    } }
                )
            }
        }
    )
}

@Composable
fun NettoApp(
    navController: NavHostController = rememberNavController()
){
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController
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
                enterTransition = { fadeIn(animationSpec = tween(1000))},
                exitTransition = { fadeOut(animationSpec = tween(1000))}
            ){
                RecipeScreen(
                    navigateToEditRecipe = { navController.navigate("${NettoScreen.EditRecipeScreen.name}/$it") }
                )
            }

            composable(
                route = RecipeDetailsDestination.routeWithArgs,
                exitTransition = {
                    slideOutHorizontally(animationSpec = tween(1000), targetOffsetX = { it})
                                 },
                enterTransition = {
                    slideInHorizontally(animationSpec = tween(1000), initialOffsetX = {-it})
                                  },
                arguments = listOf(navArgument(RecipeDetailsDestination.recipeIdArg) {
                    type = NavType.IntType
                })
            ) {
                RecipeScreen(
                    navigateToEditRecipe = {
                        navController.navigate("${NettoScreen.EditRecipeScreen.name}/$it")
                    }
                )
            }

            composable(
                route = NettoScreen.EditRecipeScreen.name,
                enterTransition = { fadeIn(animationSpec = tween(1000))},
                exitTransition = { fadeOut(animationSpec = tween(1000))}
            ){
                EditRecipeScreen(
                    navigateBack = { navController.navigateUp()
                    }
                )
            }

            composable(
                route = EditRecipeDetailsDestination.routeWithArgs,
                enterTransition = { fadeIn(animationSpec = tween(1000))},
                exitTransition = { fadeOut(animationSpec = tween(1000))},
                arguments = listOf(navArgument(EditRecipeDetailsDestination.recipeIdArg) {
                    type = NavType.IntType
                })
            ) {
                EditRecipeScreen(
                    navigateBack = { navController.navigateUp() }
                )
            }

            composable(
                route = NettoScreen.ListOfRecipesScreen.name,
                enterTransition = { fadeIn(animationSpec = tween(1000))},
                exitTransition = { fadeOut(animationSpec = tween(1000))}
            ){
                ListOfRecipesScreen(
                    onRecipeCardClicked = {
                        navController.navigate("${NettoScreen.RecipeScreen.name}/$it")
                    },
                    navigateToRecipeEntry = { navController.navigate(NettoScreen.EditRecipeScreen.name) }
                )
            }

            composable(
                route = NettoScreen.ListOfIngredientsScreen.name,
                enterTransition = { fadeIn(animationSpec = tween(1000))},
                exitTransition = { fadeOut(animationSpec = tween(1000))}
            ){
                ListOfIngredientsScreen(
                    navigateToIngredientUpdate = {
                        navController.navigate("${NettoScreen.IngredientScreen.name}/$it") },
                    navigateToIngredientEntry = {navController.navigate(NettoScreen.IngredientScreen.name)}
                )
            }

            composable(
                route = IngredientDetailsDestination.routeWithArgs,
                enterTransition = { fadeIn(animationSpec = tween(1000))},
                exitTransition = { fadeOut(animationSpec = tween(1000))},
                arguments = listOf(navArgument(IngredientDetailsDestination.ingredientIdArg) {
                    type = NavType.IntType
                })
            ) {
                IngredientScreen(
                    navigateBack = { navController.navigateUp() }
                )
            }

            composable(
                route = NettoScreen.IngredientScreen.name,
                enterTransition = { fadeIn(animationSpec = tween(1000))},
                exitTransition = { fadeOut(animationSpec = tween(1000))}
            ){
                IngredientScreen(navigateBack = { navController.navigateUp() })
            }

            composable(
                route = NettoScreen.ProfileScreen.name,
                enterTransition = { fadeIn(animationSpec = tween(1000))},
                exitTransition = { fadeOut(animationSpec = tween(1000))}
            ){
                ProfileScreen()
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
            BottomNavigationBar(navController = rememberNavController())
        }
    }
}