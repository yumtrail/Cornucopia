package com.appkitchen.cornucopia.repo

import com.appkitchen.cornucopia.db.RestaurantDao
import com.appkitchen.cornucopia.db.RestaurantsFoods

class RestaurantRepo(private val restaurantDao: RestaurantDao) {

    val restaurantsAndFoods: List<RestaurantsFoods> = restaurantDao.getRestaurantsAndFoods()

}