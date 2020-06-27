package com.deledzis.localshare.util

import com.deledzis.localshare.util.extensions.ignoreTime
import java.text.SimpleDateFormat
import java.util.*

abstract class DateUtils {

    companion object {
        val ISO_8601_24H_FULL_FORMAT = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale("ru"))
        val ISO_24H_FORMAT = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale("ru"))
        val DF_ONLY_DAY = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))
        val DF_WEEK_DAY_TIME = SimpleDateFormat("EEEE, d MMMM, в HH:mm МСК", Locale("ru"))
        val DF_DAY_TIME_TIME = SimpleDateFormat("dd MMMM yyyy в HH:mm МСК", Locale("ru"))
        val RU_DATE_FORMAT = SimpleDateFormat("dd.MM.yyyy", Locale("ru"))
        val SIMPLE_FORMAT = SimpleDateFormat("yyyy-MM-dd", Locale("ru"))
        val OUTPUT_SIMPLE_FORMAT = SimpleDateFormat("dd MMMM", Locale("ru"))

        fun getDate(year: Int, month: Int, day: Int) = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, day)
        }.time.ignoreTime()

        fun getDate(date: String, format: SimpleDateFormat = ISO_8601_24H_FULL_FORMAT) =
            format.parse(date)

        fun getCurrentDate(): Date = Calendar.getInstance().time

        fun getDateWithOffset(
            fromDate: Date? = null,
            seconds: Int = 0,
            minutes: Int = 0,
            hours: Int = 0,
            days: Int = 0
        ): Date {
            val date = Calendar.getInstance()
            fromDate?.let { date.time = it }
            date.add(Calendar.SECOND, seconds)
            date.add(Calendar.MINUTE, minutes)
            date.add(Calendar.HOUR_OF_DAY, hours)
            date.add(Calendar.DATE, days)
            return date.time
        }
    }
}
