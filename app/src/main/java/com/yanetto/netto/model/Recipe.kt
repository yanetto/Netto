package com.yanetto.netto.model
class Recipe(
    var name: String,
    var description: String,
    var servingsCount: Int,
    var ingredientList: List<IngredientInRecipe>
){
    val totalPrice = ingredientList.sumOf { it.ingredient.pricePerKg/1000 * it.weight.toDouble()}.toFloat()
    val totalWeight = ingredientList.sumOf {it.weight.toDouble()}.toFloat()
    val energy = ingredientList.sumOf {it.ingredient.energy.toDouble()}.toFloat()
    val protein = ingredientList.sumOf {it.ingredient.protein.toDouble()}.toFloat()
    val fat = ingredientList.sumOf {it.ingredient.fat.toDouble()}.toFloat()
    val carbohydrates = ingredientList.sumOf {it.ingredient.carbohydrates.toDouble()}.toFloat()
    fun getIngredientWeight(index: Int, newTotalWeight: Float): Float{
        val ingredient = ingredientList[index]
        return ingredient.weight * newTotalWeight / totalWeight
    }
}
