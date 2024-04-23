package com.yanetto.netto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.yanetto.netto.ui.NettoApp
import com.yanetto.netto.ui.listOfRecipesScreen.ListOfRecipesScreen
import com.yanetto.netto.ui.recipeScreen.RecipeScreen
import com.yanetto.netto.ui.theme.NettoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NettoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NettoApp()
                }
            }
        }
    }
}