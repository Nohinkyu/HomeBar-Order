package com.devik.homebarorder.util

import java.text.DecimalFormat

object TextFormatUtil {

    val thousandsComma = DecimalFormat("#,###")

    fun priceTextFormat(price: Number, measure: String):String {
        return "${thousandsComma.format(price)} $measure"
    }
}