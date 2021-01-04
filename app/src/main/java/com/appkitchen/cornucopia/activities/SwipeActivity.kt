package com.appkitchen.cornucopia.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.databinding.DataBindingUtil
import com.appkitchen.cornucopia.R
import com.appkitchen.cornucopia.databinding.ActivitySwipeBinding
import com.appkitchen.cornucopia.models.CardModel
import com.appkitchen.cornucopia.models.FoodCardViewModel
import com.bumptech.glide.Glide

class SwipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySwipeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_swipe)
        val model: FoodCardViewModel by viewModels()
        model.modelStream.observe(this, { bind(it) })
        binding.foodLayout.setTransitionListener(object : TransitionAdapter() {
            override fun onTransitionCompleted(motionLayout: MotionLayout, currentId: Int) {
                when (currentId) {
                    R.id.offScreenLike, R.id.offScreenPass -> {
                        model.swipe()
                        motionLayout.progress = 0f
                    }
                }
            }
        })
    }

    private fun bind(model: CardModel) {
        Glide.with(this).load(model.bottom.imgUrl).dontAnimate()
            .let { request ->
                if (binding.bottom.drawable != null) {
                    request.placeholder(binding.bottom.drawable.constantState?.newDrawable()?.mutate())
                } else {
                    request
                }
            }.into(binding.bottom)
        Glide.with(this).load(model.top.imgUrl).dontAnimate()
            .let { request ->
                if (binding.top.drawable != null) {
                    request.placeholder(binding.bottom.drawable.constantState?.newDrawable()?.mutate())
                } else {
                    request
                }
            }.into(binding.top)
    }
}