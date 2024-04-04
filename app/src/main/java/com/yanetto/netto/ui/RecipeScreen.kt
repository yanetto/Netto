package com.yanetto.netto.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yanetto.netto.R
import com.yanetto.netto.ui.theme.NettoTheme

@Composable
fun RecipeScreen(
    modifier: Modifier = Modifier
){
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = "Sticky Red Wine Shallots With Oregano Polenta",
            fontSize = 36.sp,
            modifier = modifier
                .padding(8.dp),
            lineHeight = 40.sp
        )

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .width(1.dp)
                .padding(8.dp)
        )

        Text(
            text = "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription",
            fontSize = 24.sp,
            modifier = modifier
                .padding(8.dp),
            lineHeight = 28.sp
        )

        ServingsCard(modifier = modifier)

//        Row(
//            modifier = modifier
//                .padding(8.dp)
//        ) {
//            val colors = ButtonDefaults.outlinedButtonColors(
//                containerColor = MaterialTheme.colorScheme.surface,
//                contentColor = MaterialTheme.colorScheme.onSurface,
//                disabledContainerColor = MaterialTheme.colorScheme.primary,
//                disabledContentColor = MaterialTheme.colorScheme.onPrimary
//            )
//            var isIngredientsSelected by remember { mutableStateOf(true) }
//            OutlinedButton(
//                enabled = !isIngredientsSelected,
//                onClick = { isIngredientsSelected = !isIngredientsSelected },
//                colors = colors,
//                modifier = modifier
//                    .weight(1f, true)
//            ) {
//                Text(
//                    text = stringResource(R.string.ingredients)
//                )
//            }
//            Spacer(modifier = modifier.weight(0.1f, true))
//            OutlinedButton(
//                enabled = isIngredientsSelected,
//                onClick = { isIngredientsSelected = !isIngredientsSelected },
//                colors = colors,
//                modifier = modifier
//                    .weight(1f, true)
//            ) {
//                Text(text = stringResource(R.string.nutritional_info))
//            }
//        }
        


        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            item{
                Text(text = "Ingredients", style = MaterialTheme.typography.headlineMedium)
            }
            items(4) {
                IngredientItem(modifier)
            }
        }

        Row(
            modifier = modifier
                .padding(8.dp)
        ) {
            val colors = ButtonDefaults.outlinedButtonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                disabledContainerColor = MaterialTheme.colorScheme.primary,
                disabledContentColor = MaterialTheme.colorScheme.onPrimary
            )
            OutlinedButton(
                onClick = {  },
                colors = colors,
                modifier = modifier
                    .weight(1f, true)
            ) {
                Text(
                    text = stringResource(R.string._100_g)
                )
            }
            Spacer(modifier = modifier.weight(0.05f, true))
            OutlinedButton(
                enabled = false,
                onClick = {  },
                modifier = modifier
                    .weight(1f, true),
                colors = colors
            ) {
                Text(stringResource(R.string.serving))
            }
            Spacer(modifier = modifier.weight(0.05f, true))
            OutlinedButton(
                onClick = {  },
                modifier = modifier
                    .weight(1f, true),
                colors = colors,
            ) {
                Text(stringResource(R.string.total))
            }
        }
        
        
        Spacer(modifier = modifier.padding(4.dp))
        PriceAndWeightLabels(label = "Price:", number = "100 rub", modifier = modifier)
        PriceAndWeightLabels(label = "Weight:", number = "200 g", modifier = modifier)
    }
}

@Composable
fun PriceAndWeightLabels(
    label: String,
    number: String,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            fontSize = 20.sp,
            modifier = modifier,
            text = label,
            textAlign = TextAlign.Start
        )
        Spacer(modifier = modifier.weight(1f, true))
        Text(
            fontSize = 20.sp,
            modifier = modifier,
            text = number,
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun IngredientItem(
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Red wine",
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Clip
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = "description",
                style = MaterialTheme.typography.labelLarge
            )
        }

        Spacer(modifier = modifier.weight(1f, true))
        Text(
            modifier = modifier,
            text = "93.5 g",
            textAlign = TextAlign.End
        )
    }
    Divider(modifier = Modifier
        .fillMaxWidth()
        .width(1.dp)
    )
}

@Composable
fun ServingsCard(
    modifier:Modifier = Modifier
){
    OutlinedCard(
        shape = ButtonDefaults.shape,
        modifier = modifier
            .padding(8.dp)
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ){
            IconButton(onClick = { /*TODO*/ },
                modifier = modifier
                    .padding(0.dp)
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.remove_circle_black_36dp),
                    contentDescription = null,
                    modifier = modifier,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Text(
                text = "3 Servings",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                modifier = modifier
                    .weight(4f, true)
            )

            IconButton(onClick = { /*TODO*/ },
                modifier = modifier
                    .padding(0.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add_circle_black_36dp),
                    contentDescription = null,
                    modifier = modifier,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}


@Preview
@Composable
fun RecipeScreenPreview(){
    NettoTheme {
        Surface (
            modifier = Modifier.fillMaxSize()
        ){
            RecipeScreen()
        }
    }
}