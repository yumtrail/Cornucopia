package com.appkitchen.cornucopia

import android.app.Application
import com.appkitchen.cornucopia.db.YumDatabase
import com.appkitchen.cornucopia.repo.FoodCardRepo

class YumApplication : Application() {
    private val database by lazy { YumDatabase.getDatabase(this) }
    val cardRepo by lazy { FoodCardRepo(database.restaurantDao()) }
}