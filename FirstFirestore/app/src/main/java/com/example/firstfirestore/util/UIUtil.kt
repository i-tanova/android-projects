package com.example.firstfirestore.util

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.Window
import kotlin.math.roundToInt


private fun dpToPx(context: Context, dp: Int): Int {
    val r: Resources = context.resources
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        r.displayMetrics
    ).roundToInt()
}

private fun whiteNotificationBar(window: Window, view: View) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        var flags: Int = view.getSystemUiVisibility()
        flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        view.setSystemUiVisibility(flags)
        window.setStatusBarColor(Color.WHITE)
    }
}