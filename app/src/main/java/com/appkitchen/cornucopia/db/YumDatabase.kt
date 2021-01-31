package com.appkitchen.cornucopia.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.appkitchen.cornucopia.BuildConfig
import com.appkitchen.cornucopia.com.appkitchen.cornucopia.utils.Converters

@Database(entities = [Restaurant::class, Food::class],
    version = BuildConfig.YUM_DB_VERSION,
    exportSchema = true)
@TypeConverters(Converters::class)
abstract class YumDatabase : RoomDatabase() {
    abstract fun restaurantDao(): RestaurantDao

    companion object {

        @Volatile
        private var INSTANCE: YumDatabase? = null

        fun getDatabase(context: Context): YumDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    YumDatabase::class.java,
                    "yum_db"
                ).createFromAsset("databases/yum.db").fallbackToDestructiveMigration()
                    .allowMainThreadQueries().build()
                INSTANCE = instance
                instance
            }
        }
    }
}