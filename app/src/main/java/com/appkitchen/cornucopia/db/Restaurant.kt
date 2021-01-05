package com.appkitchen.cornucopia.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurant_table")
data class Restaurant(
    @PrimaryKey val restaurantId: Long,
    val name: String,
    val phoneNumber: String,
    val address: String,
    val logoUrl: String,
    val outsideImgUrl: String,
    val cuisines: List<String>,
    val priceBracket: Int
)