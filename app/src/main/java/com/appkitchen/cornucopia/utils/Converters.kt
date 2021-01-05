package com.appkitchen.cornucopia.com.appkitchen.cornucopia.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.math.BigDecimal

class Converters {
    @TypeConverter
    fun toListOfStrings(string: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(string, listType)
    }

    @TypeConverter
    fun fromListOfStrings(list: List<String>): String = Gson().toJson(list)


    @TypeConverter
    fun bigDecimalToDouble(input: BigDecimal): Double {
        return input.toDouble()
    }

    @TypeConverter
    fun doubleToBigDecimal(input: Double): BigDecimal {
        return BigDecimal.valueOf(input)
    }
}