@file:OptIn(ExperimentalMaterial3Api::class)

package com.yanetto.netto.ui.ingredientScreen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
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
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.yanetto.netto.R
import com.yanetto.netto.ui.theme.NettoTheme
import kotlinx.coroutines.launch


object IngredientDetailsDestination {
    const val route = "IngredientScreen"
    const val ingredientIdArg = "itemId"
    const val routeWithArgs = "$route/{$ingredientIdArg}"
}

@Composable
fun IngredientScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit = {},
    ingredientViewModel: IngredientViewModel = viewModel(factory = IngredientViewModel.Factory),
){
    val coroutineScope = rememberCoroutineScope()
    val ingredientUiState by ingredientViewModel.ingredientUiState.collectAsState()
    var selectedImage by remember { mutableStateOf<Uri?>(null)}
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
            selectedImage = it
        }

    val scaffoldState = rememberBottomSheetScaffoldState()


    BottomSheetScaffold (
        modifier = modifier.fillMaxWidth(),
        scaffoldState = scaffoldState,
        sheetPeekHeight = LocalConfiguration.current.screenHeightDp.dp * 0.5f,
        sheetContent = {
            LazyColumn (
                modifier = modifier
                    .height(LocalConfiguration.current.screenHeightDp.dp)
                    .fillMaxSize()
                    .scrollable(
                        orientation = Orientation.Vertical,
                        state = rememberScrollState()
                    )
            ) {
                item {
                    IngredientNameLabel(
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
                        ingredientDetails = ingredientUiState.ingredientDetails,
                        onValueChange = ingredientViewModel::updateUiState
                    )

                    HorizontalDivider(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                            .fillMaxWidth()
                    )

                    IngredientManufacturerLabel(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        ingredientDetails = ingredientUiState.ingredientDetails,
                        onValueChange = ingredientViewModel::updateUiState
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Label(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        labelText = stringResource(id = R.string.nutritional_info)
                    )

                    NutritionalIngredientItems(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        param = stringResource(R.string.energy),
                        info = stringResource(R.string._kcal),
                        ingredientDetail = ingredientUiState.ingredientDetails.energy,
                        onValueChange = ingredientViewModel::updateEnergy
                    )

                    NutritionalIngredientItems(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        param = stringResource(R.string.protein),
                        info = stringResource(R.string._g),
                        ingredientDetail = ingredientUiState.ingredientDetails.protein,
                        onValueChange = ingredientViewModel::updateProtein
                    )

                    NutritionalIngredientItems(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        param = stringResource(R.string.fat), info = stringResource(R.string._g),
                        ingredientDetail = ingredientUiState.ingredientDetails.fat,
                        onValueChange = ingredientViewModel::updateFat
                    )

                    NutritionalIngredientItems(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        param = stringResource(R.string.carbohydrates),
                        info = stringResource(R.string._g),
                        ingredientDetail = ingredientUiState.ingredientDetails.carbohydrates,
                        onValueChange = ingredientViewModel::updateCarbohydrates
                    )

                    HorizontalDivider(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                            .fillMaxWidth()
                    )

                    WeightAndPriceItems(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        label = stringResource(id = R.string.weight),
                        info = stringResource(R.string._g),
                        ingredientDetail = ingredientUiState.ingredientDetails.weight,
                        onValueChange = ingredientViewModel::updateWeight
                    )

                    WeightAndPriceItems(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        label = stringResource(id = R.string.price),
                        info = stringResource(R.string._rub),
                        ingredientDetail = ingredientUiState.ingredientDetails.price,
                        onValueChange = ingredientViewModel::updatePrice
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    ){

        Box(
            modifier = Modifier
                .height(LocalConfiguration.current.screenHeightDp.dp * 0.5f)
                .fillMaxWidth()
                .paint(painter = rememberAsyncImagePainter(model = selectedImage), alignment = Alignment.TopCenter, contentScale = ContentScale.FillHeight)
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, start = 8.dp, end = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        enabled = true,
                        onClick = { galleryLauncher.launch("image/*") },
                        modifier = Modifier
                            .size(48.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.add_a_photo_24dp),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    IconButton(
                        enabled = true,
                        onClick = {
                            coroutineScope.launch { ingredientViewModel.saveItem() }
                            navigateBack()
                        },
                        modifier = Modifier
                            .size(48.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.done_24dp),
                            contentDescription = null,
                            modifier = Modifier,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Label(
    modifier: Modifier = Modifier,
    labelText: String
){
    Text(
        text = labelText,
        style = MaterialTheme.typography.headlineLarge,
        modifier = modifier.padding(8.dp)
    )
}

@Composable
fun IngredientManufacturerLabel(
    modifier: Modifier = Modifier,
    ingredientDetails: IngredientDetails,
    onValueChange: (IngredientDetails) -> Unit = {}
){
    val focusManager = LocalFocusManager.current
    var textValue by remember{ mutableStateOf(TextFieldValue("")) }
    val interactionSource = remember{ MutableInteractionSource() }
    val isFocused = interactionSource.collectIsFocusedAsState()
    val valueHint = ingredientDetails.manufacturer

    LaunchedEffect(isFocused.value){
        if(!isFocused.value) textValue = TextFieldValue("")
    }

    val color = MaterialTheme.colorScheme.onSurface
    val hintColor = if(isFocused.value) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f) else MaterialTheme.colorScheme.onSurface

    BasicTextField(
        value = textValue,
        onValueChange = {
            textValue = it
            onValueChange(ingredientDetails.copy(manufacturer = it.text)) },
        singleLine = true,
        modifier = modifier.padding(8.dp),
        textStyle = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Start, color = color),
        interactionSource = interactionSource,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        keyboardActions = KeyboardActions(onDone  = {
            focusManager.clearFocus()
        }),
        cursorBrush = SolidColor(color)
    ){
        Box(
            modifier = Modifier.fillMaxWidth()
        ){
            if(textValue.text.isEmpty()){
                Text(
                    text = valueHint,
                    style = MaterialTheme.typography.titleLarge,
                    color = hintColor,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
            }
            it()
        }
    }
}

@Composable
fun IngredientNameLabel(
    modifier: Modifier = Modifier,
    ingredientDetails: IngredientDetails,
    onValueChange: (IngredientDetails) -> Unit = {}
){
    val focusManager = LocalFocusManager.current
    var textValue by remember{ mutableStateOf(TextFieldValue("")) }
    val interactionSource = remember{ MutableInteractionSource() }
    val isFocused = interactionSource.collectIsFocusedAsState()
    val valueHint = ingredientDetails.name

    LaunchedEffect(isFocused.value){
        if(!isFocused.value) textValue = TextFieldValue("")
    }

    val color = MaterialTheme.colorScheme.onSurface
    val hintColor = if(isFocused.value) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f) else MaterialTheme.colorScheme.onSurface

    BasicTextField(
        value = textValue,
        onValueChange = {
            textValue = it
            onValueChange(ingredientDetails.copy(name = it.text)) },
        singleLine = true,
        modifier = modifier.padding(8.dp),
        textStyle = MaterialTheme.typography.displaySmall.copy(textAlign = TextAlign.Start, color = color),
        interactionSource = interactionSource,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        keyboardActions = KeyboardActions(onDone  = {
            focusManager.clearFocus()
        }),
        cursorBrush = SolidColor(color)
    ){
        Box(
            modifier = Modifier.fillMaxWidth()
        ){
            if(textValue.text.isEmpty()){
                Text(
                    text = valueHint,
                    style = MaterialTheme.typography.displaySmall,
                    color = hintColor,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
            }
            it()
        }
    }
}

@Composable
fun WeightAndPriceItems(
    modifier: Modifier = Modifier,
    label: String,
    info: String,
    ingredientDetail: String,
    onValueChange: (String) -> Unit = {}
){
    val focusManager = LocalFocusManager.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.weight(2f)
        )

        Spacer(modifier = Modifier.size(16.dp))

        var textValue by remember{ mutableStateOf(TextFieldValue("")) }
        val interactionSource = remember{ MutableInteractionSource() }
        val isFocused = interactionSource.collectIsFocusedAsState()
        val valueHint = ingredientDetail

        LaunchedEffect(isFocused.value){
            if(!isFocused.value) textValue = TextFieldValue("")
        }

        val color = if(textValue.text.toFloatOrNull() != null || textValue.text.isEmpty()) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.errorContainer
        val hintColor = if(isFocused.value) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f) else MaterialTheme.colorScheme.onSurface

        BasicTextField(
            value = textValue,
            onValueChange = {
                textValue = it
                onValueChange(it.text) },
            singleLine = true,
            modifier = Modifier
                .weight(1f),
            textStyle = MaterialTheme.typography.headlineMedium.copy(textAlign = TextAlign.End, color = color),
            interactionSource = interactionSource,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            keyboardActions = KeyboardActions(onDone  = {
                focusManager.clearFocus()
            }),
            cursorBrush = SolidColor(color)
        ){
            Box(
                modifier = Modifier.fillMaxWidth()
            ){
                if(textValue.text.isEmpty()){
                    Text(
                        text = valueHint,
                        style = MaterialTheme.typography.headlineMedium,
                        color = hintColor,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }
                it()
            }
        }

        Text(
            text = info,
            style = MaterialTheme.typography.headlineMedium,
            color = color
        )
    }
}


@Composable
fun NutritionalIngredientItems(
    modifier: Modifier = Modifier,
    param: String,
    info: String,
    ingredientDetail: String,
    onValueChange: (String) -> Unit = {}
){
    HorizontalDivider(
        modifier = modifier
            .padding(start = 8.dp, end = 8.dp)
            .fillMaxWidth()
            .width(1.dp)
    )
    val focusManager = LocalFocusManager.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = param,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(2f)
        )

        Spacer(modifier = Modifier.size(16.dp))

        var textValue by remember{ mutableStateOf(TextFieldValue("")) }
        val interactionSource = remember{ MutableInteractionSource() }
        val isFocused = interactionSource.collectIsFocusedAsState()
        val valueHint = ingredientDetail

        LaunchedEffect(isFocused.value){
            if(!isFocused.value) textValue = TextFieldValue("")
        }

        val color = if(textValue.text.toFloatOrNull() != null || textValue.text.isEmpty()) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.errorContainer
        val hintColor = if(isFocused.value) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f) else MaterialTheme.colorScheme.onSurface

        BasicTextField(
            value = textValue,
            onValueChange = {
                textValue = it
                onValueChange(it.text) },
            singleLine = true,
            modifier = Modifier
                .weight(1f),
            textStyle = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.End, color = color),
            interactionSource = interactionSource,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            keyboardActions = KeyboardActions(onDone  = {
                if (textValue.text.last() == '.'){
                    textValue = TextFieldValue(textValue.text + '0')
                    onValueChange(textValue.text)
                }
                focusManager.clearFocus()
            }),
            cursorBrush = SolidColor(color)
        ){
            Box(
                modifier = Modifier.fillMaxWidth()
            ){
                if(textValue.text.isEmpty()){
                    Text(
                        text = valueHint,
                        style = MaterialTheme.typography.titleMedium,
                        color = hintColor,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }
                it()
            }
        }

        Text(
            text = info,
            style = MaterialTheme.typography.titleMedium,
            color = color
        )
    }

}

@Preview
@Composable
fun IngredientScreenPreview(){
    NettoTheme {
        Surface (
            modifier = Modifier.fillMaxSize()
        ){
            IngredientScreen()
        }
    }
}