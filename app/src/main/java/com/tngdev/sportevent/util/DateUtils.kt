package com.tngdev.sportevent.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun formatDateStr(dateline: String?): String {
    var dateFormat = ""
    if (dateline != null) {
        val formatStrings = listOf(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
//            "EEE MMM dd HH:mm:ss 'GMT+07:00' yyyy"
        )
        formatStrings.map { f ->
            try {
                val parse = SimpleDateFormat(f, Locale.US)
                parse.timeZone = TimeZone.getTimeZone("Etc/UTC")
                val format = SimpleDateFormat("MMM dd yyyy", Locale.US)
                dateFormat = format.format(parse.parse(dateline)!!)
            } catch (e: ParseException) {
//            e.printStackTrace()
            }
        }
    }
    return dateFormat
}
fun formatTimeStr(dateline: String?): String {
    var dateFormat = ""
    if (dateline != null) {
        val formatStrings = listOf(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
//            "EEE MMM dd HH:mm:ss 'GMT+07:00' yyyy"
        )
        formatStrings.map { f ->
            try {
                val parse = SimpleDateFormat(f, Locale.getDefault())
                parse.timeZone = TimeZone.getTimeZone("Etc/UTC")
                val format = SimpleDateFormat("HH:mm", Locale.getDefault())
                dateFormat = format.format(parse.parse(dateline)!!)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }
    }
    return dateFormat
}

fun convertTimeStr(dateline: String?): Long {
    var dateLong = 0L
    if (dateline != null) {
        val formatStrings = listOf(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
//            "EEE MMM dd HH:mm:ss 'GMT+07:00' yyyy"
        )
        formatStrings.map { f ->
            try {
                val parse = SimpleDateFormat(f, Locale.getDefault())
                parse.timeZone = TimeZone.getTimeZone("Etc/UTC")
                dateLong = parse.parse(dateline)?.time ?: 0L
            } catch (e: ParseException) {
            e.printStackTrace()
            }
        }
    }
    return dateLong
}