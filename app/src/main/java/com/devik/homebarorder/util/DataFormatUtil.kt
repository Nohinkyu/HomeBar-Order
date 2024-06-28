package com.devik.homebarorder.util

import android.content.res.Resources
import android.os.Build
import java.text.SimpleDateFormat
import java.util.Locale

object DataFormatUtil {

    private val currentLocale: Locale
        get() {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Resources.getSystem().configuration.locales.get(0)
            } else {
                Resources.getSystem().configuration.locale
            }
        }

    private const val DATE_YEAR_MONTH_DAY_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss"

    private val dataFormat = SimpleDateFormat(DATE_YEAR_MONTH_DAY_TIME_PATTERN, currentLocale)
    fun getCurrentTime(): String {
        return dataFormat.format(System.currentTimeMillis())
    }
}