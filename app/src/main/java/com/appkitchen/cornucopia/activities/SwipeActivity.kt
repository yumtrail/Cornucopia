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
        val drawable = binding.bottom.drawable
        binding.bottom.prevDrawable = drawable
        binding.bottom.tempDrawable = drawable
        binding.bottom.imgUrls = model.bottom.food.imgUrls
        binding.top.prevDrawable = binding.top.drawable
        binding.top.tempDrawable = drawable
        binding.top.imgUrls = model.top.food.imgUrls
    }
}