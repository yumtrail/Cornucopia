package com.appkitchen.cornucopia.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurant_table")
data class Restaurant(
    @PrimaryKey(autoGenerate = false) val restaurantId: Long = 0,
    val name: String,
)