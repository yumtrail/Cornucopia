package com.appkitchen.cornucopia.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.appkitchen.cornucopia.com.appkitchen.cornucopia.utils.Converters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Restaurant::class, Food::class], version = 1)
@TypeConverters(Converters::class)
abstract class YumDatabase : RoomDatabase() {
    abstract fun restaurantDao(): RestaurantDao

    private class DatabaseCallback(
        private val scope: CoroutineScope,
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val restaurantDao = database.restaurantDao()
                    restaurantDao.deleteAll()

                    var restaurant = Restaurant(0, "Cravings")
                    var foods = listOf(
                        Food(
                            1,
                            0,
                            listOf("https://houseofyumm.com/wp-content/uploads/2018/03/taco-seasoning.jpg")
                        ),
                        Food(
                            2,
                            0,
                            listOf("https://kirbiecravings.com/wp-content/uploads/2019/11/char-siu-11.jpg")
                        ),
                        Food(
                            3,
                            0,
                            listOf("https://cdn1.girlgonegourmet.com/wp-content/uploads/2020/08/French-Onion-Hot-Dogs-9.jpg")
                        )
                    )
                    restaurantDao.insert(restaurant, foods)
                    restaurant = Restaurant(4, "Burrito King")
                    foods = listOf(
                        Food(
                            5,
                            4,
                            listOf("https://www.halfbakedharvest.com/wp-content/uploads/2020/02/30-Minute-Creamy-Sesame-Miso-Ramen-with-Crispy-Mushrooms-1.jpg")
                        ),
                        Food(
                            6,
                            4,
                            listOf("https://thegirlonbloor.com/wp-content/uploads/2015/03/Pulled-Chicken-Burrito-2.jpg")
                        ),
                        Food(
                            7,
                            4,
                            listOf("https://thegirlonbloor.com/wp-content/uploads/2015/08/Easy-Korean-Bibimbap-6.jpg")
                        )
                    )
                    restaurantDao.insert(restaurant, foods)
                }
            }
        }
    }

    companion object {

        @Volatile
        private var INSTANCE: YumDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): YumDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    YumDatabase::class.java,
                    "yum_db"
                ).addCallback(DatabaseCallback(scope)).allowMainThreadQueries().build()
                INSTANCE = instance
                instance
            }
        }
    }
}