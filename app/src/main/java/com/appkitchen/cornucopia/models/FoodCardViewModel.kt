package com.appkitchen.cornucopia.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FoodCardViewModel : ViewModel() {
    private val stream = MutableLiveData<CardModel>()
    val modelStream: LiveData<CardModel>
        get() = stream

    private val data = listOf(
        FoodCardModel(imgUrl = "https://houseofyumm.com/wp-content/uploads/2018/03/taco-seasoning.jpg"),
        FoodCardModel(imgUrl = "https://kirbiecravings.com/wp-content/uploads/2019/11/char-siu-11.jpg"),
        FoodCardModel(imgUrl = "https://cdn1.girlgonegourmet.com/wp-content/uploads/2020/08/French-Onion-Hot-Dogs-9.jpg"),
        FoodCardModel(imgUrl = "https://thegirlonbloor.com/wp-content/uploads/2015/03/Pulled-Chicken-Burrito-2.jpg"),
        FoodCardModel(imgUrl = "https://thegirlonbloor.com/wp-content/uploads/2015/08/Easy-Korean-Bibimbap-6.jpg"),
    )
    private var idx = 0

    private val topCard
        get() = data[idx % data.size]
    private val bottomCard
        get() = data[(idx + 1) % data.size]

    init {
        updateStream()
    }

    fun swipe() {
        idx++
        updateStream()
    }

    private fun updateStream() {
        stream.value = CardModel(top = topCard, bottom = bottomCard)
    }
}