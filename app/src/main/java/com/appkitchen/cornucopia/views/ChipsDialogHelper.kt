package com.appkitchen.cornucopia.com.appkitchen.cornucopia.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.appkitchen.cornucopia.R
import com.google.android.material.chip.Chip

class ChipsDialogHelper(context: Context) : BaseDialogHelper() {
    override val dialogView: View by lazy {
        LayoutInflater.from(context).inflate(R.layout.chip_dialog, null)
    }

    override val builder: AlertDialog.Builder = AlertDialog.Builder(context).setView(dialogView)

    private val moreButton: Button by lazy {
        dialogView.findViewById(R.id.moreBttn)
    }

    private val lessButton: Button by lazy {
        dialogView.findViewById(R.id.lessBttn)
    }

    private val mainIcon: Chip by lazy {
        dialogView.findViewById(R.id.chipIcon)
    }

    fun setupChip(text: CharSequence, image: Drawable) {
        mainIcon.text = text
        mainIcon.chipIcon = image
    }

    fun moreBttnClickListener(func: (() -> Unit)? = null) =
        with(moreButton) {
            setClickListenerToDialogIcon(func)
        }

    fun lessBttnClickListener(func: (() -> Unit)? = null) =
        with(lessButton) {
            setClickListenerToDialogIcon(func)
        }

    private fun View.setClickListenerToDialogIcon(func: (() -> Unit)?) =
        setOnClickListener {
            func?.invoke()
            dialog?.dismiss()
        }
}