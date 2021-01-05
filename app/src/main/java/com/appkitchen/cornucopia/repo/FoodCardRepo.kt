package com.appkitchen.cornucopia.repo

import com.appkitchen.cornucopia.db.RestaurantDao
import com.appkitchen.cornucopia.models.FoodCardModel

class FoodCardRepo(private val restaurantDao: RestaurantDao) {

    val foodCards = buildList {
        restaurantDao.getRestaurantsAndFoods().flatMap { it.foods.shuffled().take(3) }
            .mapTo(this) { FoodCardModel(it) }
    }
}