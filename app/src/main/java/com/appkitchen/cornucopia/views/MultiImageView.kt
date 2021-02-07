package com.appkitchen.cornucopia.com.appkitchen.cornucopia.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

class MultiImageView : androidx.appcompat.widget.AppCompatImageView {
    private val cornerRadius = 25
    var prevDrawable: Drawable? = null
    var tempDrawable: Drawable? = null
    var idx: Int = 0
    var bitmaps: MutableList<Bitmap> = mutableListOf()
    var imgUrls: List<String> = listOf("")
        set(value) {
            field = value
            Glide.with(this).load(value[0]).transform(RoundedCorners(cornerRadius)).dontAnimate()
                .let { request ->
                    if (prevDrawable != null) {
                        request.placeholder(tempDrawable!!.constantState?.newDrawable()?.mutate())
                    } else {
                        request
                    }
                }.into(this)
            loadBitmaps(value)
        }

    private fun loadBitmaps(urls: List<String>) {
        for (url in urls) {
            Glide.with(this).asBitmap().load(url).transform(RoundedCorners(cornerRadius))
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?,
                    ) {
                        bitmaps.add(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })
        }
    }

    fun nextImg() {
        ++idx
        this.setImageBitmap(bitmaps[idx % bitmaps.size])
    }

    fun loadFirstImg() {
        this.setImageBitmap(bitmaps[0])
    }

    fun loadSecondImg() {
        this.setImageBitmap(bitmaps[1])
    }

    fun loadThirdImg() {
        this.setImageBitmap(bitmaps[2])
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context,
        attrs,
        defStyleAttr)
}