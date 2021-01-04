package com.appkitchen.cornucopia.db

import androidx.room.*

@Dao
interface RestaurantDao {
    @Insert
    fun insertAll(vararg restaurant: Restaurant)

    @Delete
    fun delete(restaurant: Restaurant)

    @Query("SELECT * FROM restaurant_table")
    fun getAll(): List<Restaurant>

    @Transaction
    @Query("SELECT * FROM restaurant_table")
    fun getRestaurantsAndFoods(): List<RestaurantsFoods>
}