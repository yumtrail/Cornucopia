package com.appkitchen.cornucopia.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "food_table",
    foreignKeys = [ForeignKey(entity = Restaurant::class,
        parentColumns = ["restaurantId"],
        childColumns = ["ownerId"],
        onDelete = ForeignKey.CASCADE)])
data class Food(
    @PrimaryKey(autoGenerate = true) val foodId: Long,
    val ownerId: Long,
    val name: String,
    val imgUrls: List<String>,
    val price: BigDecimal,
    val diets: List<String>? = null,
)
