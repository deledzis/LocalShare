package com.deledzis.localshare.infrastructure.extensions

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

fun Date.formatDateTime(): String {
    val dt = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    dt.timeZone = TimeZone.getDefault()
    return dt.format(this)
}

fun Date.formatTime(): String {
    val dt = SimpleDateFormat("HH:mm", Locale.getDefault())
    dt.timeZone = TimeZone.getDefault()
    return dt.format(this)
}

fun Date.formatDate(): String {
    val dt = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    dt.timeZone = TimeZone.getDefault()
    return dt.format(this)
}

fun Date.formatDateMoscow(): String {
    val dt = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    dt.timeZone = TimeZone.getTimeZone("Europe/Moscow")
    return dt.format(this)
}

fun Date.ignoreTime(): Date {
    val cal = Calendar.getInstance()
    cal.time = this
    cal.set(Calendar.HOUR_OF_DAY, 0)
    cal.set(Calendar.MINUTE, 0)
    cal.set(Calendar.SECOND, 0)
    cal.set(Calendar.MILLISECOND, 0)
    time = cal.timeInMillis
    return this
}

fun Date.compareIgnoreTime(other: Date): Int {
    val thisDate = Calendar.getInstance()
        .also { it.time = this }
    val otherDate = Calendar.getInstance()
        .also { it.time = other }
    return thisDate.compareIgnoreTime(otherDate)
}

fun Date.getBigger(other: Date) = if (this > other) this else other

fun Date.getLower(other: Date) = if (this < other) this else other

@SuppressLint("SimpleDateFormat")
fun Date.convertWithTimeZone(
    timePattern: String = "yyyy-MM-dd'T'HH:mm:ss",
    timeZone: String = "Europe/Moscow"
): Date? {
    val isoFormat = SimpleDateFormat(timePattern)
    isoFormat.timeZone = TimeZone.getTimeZone(timeZone)
    val oldDate = isoFormat.format(this)
    isoFormat.timeZone = TimeZone.getDefault()
    return isoFormat.parse(oldDate)
}

fun Date.copy() = Date(time)