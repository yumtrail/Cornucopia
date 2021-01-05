package com.appkitchen.cornucopia.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.appkitchen.cornucopia.com.appkitchen.cornucopia.utils.Converters

@Database(entities = [Restaurant::class, Food::class], version = 1)
@TypeConverters(Converters::class)
abstract class YumDatabase : RoomDatabase() {
    abstract fun restaurantDao(): RestaurantDao
    abstract fun foodDao(): FoodDao

    companion object {

        @Volatile
        private var INSTANCE: YumDatabase? = null

        fun getDatabase(context: Context): YumDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    YumDatabase::class.java,
                    "yum_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}