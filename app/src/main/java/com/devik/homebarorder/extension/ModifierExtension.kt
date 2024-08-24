package com.devik.homebarorder.extension

import androidx.compose.foundation.clickable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Modifier.throttledClickable(
    coroutineScope: CoroutineScope,
    periodMillis: Long = 1000L,
    onClick: () -> Unit
): Modifier = composed {
    var lastClickTime by remember { mutableStateOf(0L) }

    this.clickable {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime >= periodMillis) {
            lastClickTime = currentTime
            coroutineScope.launch {
                onClick()
            }
        }
    }
}