package com.appkitchen.cornucopia.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Restaurant::class], version = 1, exportSchema = false)
abstract class RestaurantDb : RoomDatabase() {

    abstract fun restaurantDao(): RestaurantDao

    companion object {

        @Volatile
        private var INSTANCE: RestaurantDb? = null

        fun getDatabase(context: Context): RestaurantDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RestaurantDb::class.java,
                    "restaurant_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}