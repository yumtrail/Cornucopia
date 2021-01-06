package com.appkitchen.cornucopia.db

import androidx.room.*

@Dao
interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg food: Food)

    @Delete
    suspend fun delete(food: Food)

    @Query("SELECT * FROM food_table")
    fun getAll(): List<Food>
}