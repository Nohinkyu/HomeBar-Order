package com.devik.homebarorder.extension

import android.os.Build
import android.view.View
fun View.setImmersiveMode() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
        systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                )
    } else {
        windowInsetsController?.hide(
            android.view.WindowInsets.Type.systemBars()
        )
    }
}