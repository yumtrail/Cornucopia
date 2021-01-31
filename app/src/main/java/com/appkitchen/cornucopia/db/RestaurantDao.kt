package com.appkitchen.cornucopia.db

import androidx.room.*

@Dao
interface RestaurantDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(restaurant: Restaurant): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(food: Food)

    @Transaction
    suspend fun insert(restaurant: Restaurant, foods: List<Food>) {
        val restaurantId = insert(restaurant)
        foods.forEach {
            it.restaurantId = restaurantId
            insert(it)
        }
    }

    @Query("DELETE FROM restaurant_table")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(restaurant: Restaurant)

    @Query("SELECT * FROM restaurant_table")
    fun getRestaurantsAndFoods(): List<RestaurantsFoods>
}