package com.appkitchen.cornucopia.activities

import android.os.Bundle
import android.view.View
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
        binding.radioButton4.apply {
            setOnClickListener {
                if (isChecked) {
                    binding.top.loadImgAtIdx(3)
                }
            }
        }
    }

    private fun enableBttns(numImgs: Int) {
        binding.apply {
            when (numImgs) {
                1 -> radioButtons.visibility = View.INVISIBLE
                2 -> radioButton3.visibility = View.GONE
                2, 3 -> radioButton4.visibility = View.GONE
            }
        }
    }

    private fun resetRadioGroup() {
        binding.apply {
            radioButtons.visibility = View.VISIBLE
            radioButton1.visibility = View.VISIBLE
            radioButton2.visibility = View.VISIBLE
            radioButton3.visibility = View.VISIBLE
            radioButton4.visibility = View.VISIBLE
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
        resetRadioGroup()
        enableBttns(model.top.food.imgUrls.size)
    }

}