package com.yanetto.netto.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredients")
data class Ingredient(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    var manufacturer: String,
    var weight: Float,
    var price: Float,
    var energy: Float,
    var protein: Float,
    var fat: Float,
    var carbohydrates: Float
)
