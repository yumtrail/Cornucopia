package com.appkitchen.cornucopia.activities

import android.os.Bundle
import android.widget.RadioButton
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
        setupViews(binding, model)
    }

    private fun setupViews(binding: ActivitySwipeBinding, model: FoodCardViewModel) {
        setupLayout(binding.foodLayout, model)
        setupImgButtons(binding)
    }

    private fun setupLayout(layout: MotionLayout, model: FoodCardViewModel) {
        layout.setTransitionListener(object : TransitionAdapter() {
            override fun onTransitionCompleted(motionLayout: MotionLayout, currentId: Int) {
                when (currentId) {
                    R.id.offScreenLike -> {
                        model.swipeRight()
                        resetView(motionLayout, binding.radioButton1)
                    }
                    R.id.offScreenPass -> {
                        model.swipeLeft()
                        resetView(motionLayout, binding.radioButton1)
                    }
                }
            }
        })
    }

    private fun setupImgButtons(binding: ActivitySwipeBinding) {
        binding.radioButton1.apply {
            setOnClickListener {
                if (isChecked) {
                    binding.top.loadImgAtIdx(0)
                }
            }
        }
        binding.radioButton2.apply {
            setOnClickListener {
                if (isChecked) {
                    binding.top.loadImgAtIdx(1)
                }
            }
        }
        binding.radioButton3.apply {
            setOnClickListener {
                if (isChecked) {
                    binding.top.loadImgAtIdx(2)
                }
            }
        }
    }

    private fun resetView(layout: MotionLayout, firstButton: RadioButton) {
        layout.progress = 0f
        firstButton.isChecked = true
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