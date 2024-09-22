package com.interview.whoptest.utils

import androidx.core.net.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.formattedDate(): String {
    val formatter = SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z (zzzz)", Locale.ENGLISH)
    var date = Date()
    try {
        formatter.parse(this)?.let {
            date = it
        }
        println("Parsed Date: $date")
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return date.toString()
}