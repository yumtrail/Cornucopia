package com.appkitchen.cornucopia.com.appkitchen.cornucopia.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

class MultiImageView : androidx.appcompat.widget.AppCompatImageView {
    private val cornerRadius = 40
    var prevDrawable: Drawable? = null
    var tempDrawable: Drawable? = null
    private var drawableCache: HashMap<Int, Bitmap> = HashMap()
    private val circularProgressDrawable = CircularProgressDrawable(this.context)

    init {
        circularProgressDrawable.apply {
            strokeWidth = 10f
            centerRadius = 50f
            start()
        }
    }

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
            drawableCache.clear()
            loadDrawableCache(value)
        }

    private fun loadDrawableCache(urls: List<String>) {
        for (i in urls.indices) {
            Glide.with(this).asBitmap().load(urls[i]).dontAnimate()
                .transform(RoundedCorners(cornerRadius))
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?,
                    ) {
                        drawableCache[i] = resource
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })
        }
    }

    fun loadImgAtIdx(idx: Int) {
        if (drawableCache.containsKey(idx)) {
            this.setImageBitmap(drawableCache[idx])
        } else {
            Glide.with(this).load(imgUrls[idx]).placeholder(circularProgressDrawable)
                .transition(DrawableTransitionOptions.withCrossFade())
                .transform(RoundedCorners(cornerRadius)).into(this)
        }
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context,
        attrs,
        defStyleAttr)
}