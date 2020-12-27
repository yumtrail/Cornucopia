package com.appkitchen.cornucopia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.appkitchen.cornucopia.databinding.ActivitySwipeBinding
import com.bumptech.glide.Glide

class SwipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySwipeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_swipe)
        val model: FoodCardViewModel by viewModels()
        model.modelStream.observe(this, Observer { bind(it) })
        binding.foodLayout.setTransitionListener(object : TransitionAdapter() {
            override fun onTransitionCompleted(motionLayout: MotionLayout, currentId: Int) {
                when (currentId) {
                    R.id.offScreenLike, R.id.offScreenPass -> {
                        model.swipe()
                    }
                }
            }
        })
    }

    private fun bind(model: CardModel) {
        Glide.with(this).load(model.bottom.imgUrl).into(binding.bottom)
        Glide.with(this).load(model.top.imgUrl).into(binding.top)
    }
}