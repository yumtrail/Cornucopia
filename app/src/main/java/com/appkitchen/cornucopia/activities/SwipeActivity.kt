package com.appkitchen.cornucopia.activities

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.core.view.forEach
import androidx.core.view.forEachIndexed
import androidx.databinding.DataBindingUtil
import com.appkitchen.cornucopia.R
import com.appkitchen.cornucopia.YumApplication
import com.appkitchen.cornucopia.containsIgnoreCase
import com.appkitchen.cornucopia.databinding.ActivitySwipeBinding
import com.appkitchen.cornucopia.models.CardModel
import com.appkitchen.cornucopia.models.FoodCardViewModel
import com.appkitchen.cornucopia.models.FoodCardViewModelFactory
import com.appkitchen.cornucopia.showChipsDialog
import com.google.android.material.chip.Chip
import kotlin.random.Random

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
        setupChips(binding)
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
        binding.radioButtons.apply {
            forEachIndexed { index, _ ->
                getChildAt(index).apply {
                    setOnClickListener {
                        if ((this as AppCompatRadioButton).isChecked) {
                            binding.top.loadImgAtIdx(index)
                        }
                    }
                }
            }
        }
    }

    private fun setupChips(binding: ActivitySwipeBinding) {
        binding.attrChips.forEach { chip ->
            chip.setOnLongClickListener {
                (chip as Chip).chipIcon?.let { icon ->
                    showDialog(chip.text, icon)
                }
                true
            }
        }
    }

    private fun showDialog(text: CharSequence, image: Drawable) {
        val dialog = showChipsDialog {
            setupChip(text, image)
            moreBttnClickListener {

            }
            lessBttnClickListener {

            }
        }
        dialog.show()
    }

    private fun enableBttns(numImgs: Int) {
        binding.apply {
            when (numImgs) {
                1 -> radioButtons.visibility = View.INVISIBLE
                2 -> {
                    radioButton3.visibility = View.GONE
                    radioButton4.visibility = View.GONE
                }
                3 -> radioButton4.visibility = View.GONE
            }
        }
    }

    private fun enableViews(groups: List<ViewGroup>) {
        groups.forEach { group ->
            group.visibility = View.VISIBLE
            group.forEach {
                it.visibility = View.VISIBLE
            }
        }
    }

    private fun loadChips(model: CardModel) {
        binding.attrChips.forEach {
            (it as Chip).apply {
                if (!model.top.food.features.containsIgnoreCase(this.text.toString())) {
                    this.visibility = View.GONE
                }
            }
        }
    }

    private fun bindImgs(model: CardModel) {
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

    private fun bindText(model: CardModel) {
        binding.foodName.text = model.top.food.name
        binding.foodDesc.text = model.top.food.description
        binding.distanceAway.text = getDistance()

    }

    private fun getDistance(): String {
        return "${"%.${2}f".format(Random.nextDouble(0.1, 2.0)).toDouble()} mi."
    }

    private fun resetView(layout: MotionLayout, firstButton: RadioButton) {
        layout.progress = 0f
        firstButton.isChecked = true
    }

    private fun bind(model: CardModel) {
        bindImgs(model)
        bindText(model)
        enableViews(listOf(binding.radioButtons, binding.attrChips))
        enableBttns(model.top.food.imgUrls.size)
        loadChips(model)
    }

}