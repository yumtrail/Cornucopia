package com.appkitchen.cornucopia.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.databinding.DataBindingUtil
import com.appkitchen.cornucopia.R
import com.appkitchen.cornucopia.YumApplication
import com.appkitchen.cornucopia.databinding.ActivitySwipeBinding
import com.appkitchen.cornucopia.models.CardModel
import com.appkitchen.cornucopia.models.FoodCardViewModel
import com.appkitchen.cornucopia.models.FoodCardViewModelFactory

class SwipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySwipeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_swipe)

        val model: FoodCardViewModel by viewModels {
            FoodCardViewModelFactory((application as YumApplication).cardRepo)
        }

        model.modelStream.observe(this, { bind(it) })
        binding.nextImg.setOnClickListener {
            binding.top.nextImg()
        }
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
        val bottomDrawable = binding.bottom.drawable
        val topDrawable = binding.top.drawable
        binding.bottom.apply {
            prevDrawable = bottomDrawable
            tempDrawable = bottomDrawable
            imgUrls = model.bottom.food.imgUrls
        }
        binding.top.apply {
            prevDrawable = topDrawable
            tempDrawable = bottomDrawable
            imgUrls = model.top.food.imgUrls
        }
    }
}