package com.appkitchen.cornucopia.repo

import com.appkitchen.cornucopia.db.RestaurantDao
import com.appkitchen.cornucopia.db.RestaurantsFoods
import kotlinx.coroutines.flow.Flow

class RestaurantRepository(private val restaurantDao: RestaurantDao) {

    val restaurantsAndFoods: Flow<List<RestaurantsFoods>> = restaurantDao.getRestaurantsAndFoods()
}