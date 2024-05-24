package com.yanetto.netto.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class Recipe (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    var description: String,
    var servingsCount: Int
)