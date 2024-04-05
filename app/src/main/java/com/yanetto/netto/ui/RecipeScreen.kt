@file:OptIn(ExperimentalMaterial3Api::class)

package com.yanetto.netto.ui

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
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
    LazyColumn (
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            //.verticalScroll(rememberScrollState())
            .scrollable(
                orientation = Orientation.Vertical,
                state = rememberScrollState()
            )
    ) {
        item{
            Text(
                text = "Sticky Red Wine Shallots With Oregano Polenta",
                style = MaterialTheme.typography.displaySmall,
                modifier = modifier
                    .padding(8.dp)
            )

            Divider(
                modifier = modifier
                    .fillMaxWidth()
                    .width(1.dp)
                    .padding(8.dp)
            )

            Text(
                text = "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription",
                style = MaterialTheme.typography.titleLarge,
                modifier = modifier
                    .padding(8.dp)
            )

            ServingsCard(modifier = modifier)

            Text(
                text = "Ingredients",
                style = MaterialTheme.typography.headlineMedium,
                modifier = modifier.padding(top = 8.dp, bottom = 8.dp)
            )

            Divider(modifier = Modifier
                .fillMaxWidth()
                .width(1.dp)
            )
        }

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

        items(15) {
            IngredientItem(modifier)
        }

        item{
            Text(
                text = "Nutritional Info",
                style = MaterialTheme.typography.headlineMedium,
                modifier = modifier.padding(top = 8.dp)
            )

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
                    modifier = Modifier
                        .weight(1f, true)
                ) {
                    Text(
                        text = stringResource(R.string._100_g)
                    )
                }
                Spacer(modifier = Modifier.weight(0.05f, true))
                OutlinedButton(
                    enabled = false,
                    onClick = {  },
                    modifier = Modifier
                        .weight(1f, true),
                    colors = colors
                ) {
                    Text(stringResource(R.string.serving))
                }
                Spacer(modifier = Modifier.weight(0.05f, true))
                OutlinedButton(
                    onClick = {  },
                    modifier = Modifier
                        .weight(1f, true),
                    colors = colors,
                ) {
                    Text(stringResource(R.string.total))
                }
            }

            NutritionalInfo(modifier = Modifier)

            Spacer(modifier = Modifier.padding(4.dp))
            PriceAndWeightLabels(label = stringResource(R.string.price), number = "100 rub", modifier = Modifier)
            PriceAndWeightLabels(label = stringResource(R.string.weight), number = "200 g", modifier = Modifier)
        }

    }
}

@Composable
fun NutritionalInfo(
    modifier: Modifier = Modifier
){
    Divider(modifier = Modifier
        .fillMaxWidth()
        .width(1.dp)
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "Energy",
            textAlign = TextAlign.Start,
            maxLines = 1,
            overflow = TextOverflow.Clip
        )

        Spacer(modifier = Modifier.weight(1f, true))

        Text(
            modifier = Modifier,
            text = "204 kcal",
            textAlign = TextAlign.End
        )
    }
    Divider(modifier = Modifier
        .fillMaxWidth()
        .width(1.dp)
    )


    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "Protein",
            textAlign = TextAlign.Start,
            maxLines = 1,
            overflow = TextOverflow.Clip
        )


        Spacer(modifier = Modifier.weight(1f, true))
        Text(
            modifier = Modifier,
            text = "15 g",
            textAlign = TextAlign.End
        )
    }
    Divider(modifier = Modifier
        .fillMaxWidth()
        .width(1.dp)
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {


        Text(
            text = "Fat",
            textAlign = TextAlign.Start,
            maxLines = 1,
            overflow = TextOverflow.Clip
        )
        Spacer(modifier = Modifier.size(4.dp))


        Spacer(modifier = Modifier.weight(1f, true))
        Text(
            modifier = Modifier,
            text = "8 g",
            textAlign = TextAlign.End
        )
    }
    Divider(modifier = Modifier
        .fillMaxWidth()
        .width(1.dp)
    )


    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {


        Text(
            text = "Carbohydrates",
            textAlign = TextAlign.Start,
            maxLines = 1,
            overflow = TextOverflow.Clip
        )
        Spacer(modifier = Modifier.size(4.dp))


        Spacer(modifier = Modifier.weight(1f, true))
        Text(
            modifier = Modifier,
            text = "30 g",
            textAlign = TextAlign.End
        )
    }
    Divider(modifier = Modifier
        .fillMaxWidth()
        .width(1.dp)
    )
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
            modifier = Modifier,
            text = label,
            textAlign = TextAlign.Start
        )
        Spacer(modifier = modifier.weight(1f, true))
        Text(
            fontSize = 20.sp,
            modifier = Modifier,
            text = number,
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun IngredientItem(
    modifier: Modifier = Modifier
){
    val focusManager = LocalFocusManager.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(2f)) {
            Text(
                text = "Red wine",
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = "description",
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelLarge
            )
        }

        Spacer(modifier = Modifier.size(16.dp))

        var textValue by remember{mutableStateOf(TextFieldValue("25.5"))}

        val color = if(textValue.text.toFloatOrNull() != null) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.error


        BasicTextField(
            value = textValue,
            //colors = TextFieldDefaults.outlinedTextFieldColors(
                //focusedBorderColor = Color.Transparent,
                //unfocusedBorderColor = Color.Transparent
            //),
            onValueChange = {textValue = it},
            singleLine = true,
            modifier = Modifier
                .weight(1f),
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End, color = color),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            keyboardActions = KeyboardActions(onDone  = {focusManager.clearFocus()})
        )
        Text(
            text = "g",
            modifier = Modifier.padding(4.dp)
        )
    }
    Divider(modifier = modifier
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
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ){
            IconButton(onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(0.dp)
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.remove_circle_black_36dp),
                    contentDescription = null,
                    modifier = Modifier,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Text(
                text = "3 Servings",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                modifier = Modifier
                    .weight(4f, true)
            )

            IconButton(onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(0.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add_circle_black_36dp),
                    contentDescription = null,
                    modifier = Modifier,
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