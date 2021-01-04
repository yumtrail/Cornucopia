package com.appkitchen.cornucopia.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.appkitchen.cornucopia.objects.Address
import com.appkitchen.cornucopia.objects.Cuisine
import com.appkitchen.cornucopia.objects.PriceBracket
import java.net.URL

@Entity(tableName = "restaurant_table")
data class Restaurant(
    @PrimaryKey val restaurantId: Long,
    val name: String,
    val phoneNumber: String,
    val address: Address,
    val logoUrl: URL,
    val outsideImgUrl: URL,
    val cuisines: List<Cuisine>,
    val priceBracket: PriceBracket
)