package com.appkitchen.cornucopia

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.appkitchen.cornucopia.com.appkitchen.cornucopia.views.ChipsDialogHelper

//functions
fun Collection<String>.containsIgnoreCase(item: String) = any {
    it.equals(item, ignoreCase = true)
}

//views
inline fun Activity.showChipsDialog(func: ChipsDialogHelper.() -> Unit): AlertDialog =
    ChipsDialogHelper(this).apply{
        func()
    }.create()