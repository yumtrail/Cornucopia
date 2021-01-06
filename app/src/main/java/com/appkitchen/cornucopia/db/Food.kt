package com.appkitchen.cornucopia.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "food_table",
    foreignKeys = [ForeignKey(entity = Restaurant::class,
        parentColumns = ["restaurantId"],
        childColumns = ["restaurantId"],
        onDelete = ForeignKey.CASCADE)])
data class Food(
    @PrimaryKey(autoGenerate = true) val foodId: Long = 0,
    var restaurantId: Long = 0,
//    val name: String,
    val imgUrls: List<String>,
//    val price: BigDecimal,
//    val diets: List<String>? = null,
)
