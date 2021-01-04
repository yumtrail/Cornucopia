package com.appkitchen.cornucopia.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FoodDao {
    @Insert
    fun insertAll(vararg food: Food)

    @Delete
    fun delete(food: Food)

    @Query("SELECT * FROM food_table")
    fun getAll(): List<Food>
}