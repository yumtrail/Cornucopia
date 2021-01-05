package com.appkitchen.cornucopia.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.appkitchen.cornucopia.repo.FoodCardRepo

class FoodCardViewModel(private val repository: FoodCardRepo) : ViewModel() {
    private val stream = MutableLiveData<CardModel>()
    val modelStream: LiveData<CardModel>
        get() = stream

    private val data = repository.foodCards
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

class FoodCardViewModelFactory(private val repository: FoodCardRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FoodCardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FoodCardViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}