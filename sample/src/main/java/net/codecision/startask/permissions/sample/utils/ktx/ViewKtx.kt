package net.codecision.startask.permissions.sample.utils.ktx

import android.view.View
import net.codecision.startask.permissions.sample.utils.ClickController

fun View.setSingleClickListener(listener: (v: View) -> Unit) {
    this.setOnClickListener {
        if (ClickController.isClickAllowed()) {
            listener(this)
        }
    }
}