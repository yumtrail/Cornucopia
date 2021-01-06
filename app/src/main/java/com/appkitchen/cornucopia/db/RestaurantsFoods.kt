package com.appkitchen.cornucopia.db

import androidx.room.Embedded
import androidx.room.Relation

data class RestaurantsFoods(
    @Embedded val restaurant: Restaurant,
    @Relation(
        parentColumn = "restaurantId",
        entityColumn = "restaurantId"
    )
    val foods: List<Food>
)
