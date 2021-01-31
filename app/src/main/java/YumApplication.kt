package com.appkitchen.cornucopia

import android.app.Application
import com.appkitchen.cornucopia.db.YumDatabase
import com.appkitchen.cornucopia.repo.FoodCardRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class YumApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())
    private val database by lazy { YumDatabase.getDatabase(this) }
    val cardRepo by lazy { FoodCardRepo(database.restaurantDao()) }
}