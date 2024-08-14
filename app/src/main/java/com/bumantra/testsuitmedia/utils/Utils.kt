package com.bumantra.testsuitmedia.utils

import android.util.Log

fun isPalindrome(text: String): Boolean {
    val sanitizedStr = text.replace(" ", "")
    Log.d("CHECK", "isPalindrome: $sanitizedStr")
    val reversedStr = sanitizedStr.reversed()
    return sanitizedStr == reversedStr
}