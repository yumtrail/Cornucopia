package com.appkitchen.cornucopia.com.appkitchen.cornucopia.views

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import com.appkitchen.cornucopia.R

class YumtrailMotionLayout(context: Context, attributeSet: AttributeSet? = null) :
    MotionLayout(context, attributeSet) {

    private val viewRect = Rect()
    private val clickTimeOut: Long = 150
    private var touchStarted = false
    private var clickStartTime: Long = 0
    private var numBtns = 3

    private val imageView by lazy {
        findViewById<MultiImageView>(R.id.top)
    }

    private val radioGroup by lazy {
        findViewById<RadioGroup>(R.id.radioButtons)
    }

    private val gestureListener by lazy {
        object : GestureDetector.SimpleOnGestureListener() {
            override fun onScroll(
                e1: MotionEvent,
                e2: MotionEvent,
                distanceX: Float,
                distanceY: Float,
            ): Boolean {
                imageView.getHitRect(viewRect)
                return viewRect.contains(e1.x.toInt(), e1.y.toInt())
            }
        }
    }

    private val gestureDetector by lazy {
        GestureDetector(context, gestureListener)
    }

    init {
        setTransitionListener(object : TransitionListener {
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {

            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {

            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                touchStarted = false
            }
        })
    }

    private fun checkNextRBtn() {
        var checkedBttn: RadioButton = radioGroup.findViewById(radioGroup.checkedRadioButtonId)
        var nextIdx = (radioGroup.indexOfChild(checkedBttn) + 1) % numBtns
        imageView.loadImgAtIdx(nextIdx)
        radioGroup.clearCheck()
        (radioGroup.getChildAt(nextIdx) as RadioButton).isChecked = true
    }

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                clickStartTime = event.eventTime
            }
            MotionEvent.ACTION_UP -> {
                if (event.eventTime - clickStartTime <= clickTimeOut) {
                    checkNextRBtn()
                }
                touchStarted = false
                return super.onTouchEvent(event)
            }
            MotionEvent.ACTION_CANCEL -> {
                touchStarted = false
                return super.onTouchEvent(event)
            }
        }
        if (!touchStarted) {
            imageView.getHitRect(viewRect)
            touchStarted = viewRect.contains(event.x.toInt(), event.y.toInt())
        }
        return touchStarted && super.onTouchEvent(event)
    }
}