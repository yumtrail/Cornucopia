package com.appkitchen.cornucopia.com.appkitchen.cornucopia.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import com.bumptech.glide.Glide

class MultiImageView : androidx.appcompat.widget.AppCompatImageView {
    var prevDrawable: Drawable? = null
    var tempDrawable: Drawable? = null
    var idx: Int = 0
    var imgUrls: List<String> = listOf("")
        set(value) {
            field = value
            Glide.with(this).load(value[0]).dontAnimate().let {
                request ->
                if (prevDrawable != null) {
                    request.placeholder(tempDrawable!!.constantState?.newDrawable()?.mutate())
                } else {
                    request
                }
            }.into(this)
        }
    fun nextImg() {
        ++idx
        Glide.with(this).load(imgUrls[idx%imgUrls.size]).dontAnimate().into(this)
    }
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context,
        attrs,
        defStyleAttr)
}