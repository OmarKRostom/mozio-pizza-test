package com.mozio.omarkrostom.arch.extensions

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.math.roundToLong


/**
 * Utility class to help with date/time manipulation
 */
object DateTimeExtensions {
    /**
     * Different time and date patterns
     */
    const val DATE_PATTERN_dd_MM_yyyy = "dd/MM/yyyy"
    const val DATE_PATTERN_dd_MMM_yyyy_SPACED = "dd MMM yyyy"
    const val DATE_PATTERN_dd_MMM_yyyy_HH_mm_SPACED = "dd MMM yyyy HH:mm"
    const val DATE_PATTERN_yyyy_MM_dd = "yyyy-MM-dd"
    const val DATE_PATTERN_ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss"
    const val DATE_PATTERN_ALARM_MANAGER = "yyyy-MM-dd HH:mm:ss"

    const val A_SECOND_IN_MILLIS: Long = 1000
    const val A_SECOND = 1L
    const val A_MINUTE = 60 * A_SECOND
    const val AN_HOUR = 60 * A_MINUTE
    const val A_DAY = 24 * AN_HOUR
    const val A_WEEK = 7 * A_DAY
    const val A_MONTH = 30 * A_DAY
    const val A_YEAR = 356 * A_DAY
    const val A_DAY_IN_MILLISECS = 86400000
    const val A_WEEK_IN_MILLISECS = 604800000
    const val A_MONTH_IN_MILLISECS = 2592000000
    const val A_YEAR_IN_MILLISECS = 31540000000

    /**
     * Helper functions to manipulate timestamp formats easily
     */
    fun convertMillisToHumanDate(timeInMillis: Long, pattern: String = DATE_PATTERN_ISO_8601): String {
        val calendarInstance = Calendar.getInstance()
        calendarInstance.timeInMillis = timeInMillis

        return getDateFormatter(pattern).format(calendarInstance.time)
    }

    /**
     * Private helpers for date formatting/parsing
     */
    private fun getDateFormatter(pattern: String) = SimpleDateFormat(pattern, Locale.ENGLISH)

    /**
     * Convert a date from one pattern to another, if it fails it returns the original format instead of crashing
     */
    fun convertDateTimePattern(src: String, srcPattern: String, targetPattern: String): String {
        return try {
            val srcFormatter = getDateFormatter(srcPattern)
            val targetFormatter = getDateFormatter(targetPattern)
            targetFormatter.format(srcFormatter.parse(src))
        } catch (ex: Exception) {
            src
        }
    }

    /**
     * Convert duration to minutes
     */
    fun convertMillisToMinutes(totalDuration: Double): CharSequence? =
        (totalDuration / A_MINUTE).roundToInt().toString()


    fun getTimeDifference(hour: Int, minute: Int): Int {
        val hourAsMillis = hour * 60 * 60 * 1000
        val minuteAsMillis = minute * 60 * 1000

        val currentHourAsMillis = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) * 60 * 60 * 1000
        val currentMinuteAsMillis = Calendar.getInstance().get(Calendar.MINUTE) * 60 * 1000

        return abs((currentHourAsMillis + currentMinuteAsMillis) - (hourAsMillis + minuteAsMillis))
    }

    fun getCurrentTimeInMillis(): Long {
        val calendar = Calendar.getInstance()
        return calendar.timeInMillis
    }
    fun getCurrentTimeInMillis(hour: Int, minute: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        return calendar.timeInMillis
    }

    fun formatMillisToDate(timeInMilliseconds: Long,
                           pattern: String = DATE_PATTERN_dd_MM_yyyy): String {
        val dateFormatter = SimpleDateFormat(pattern, Locale.US)
        val calendarInstance = Calendar.getInstance()
        calendarInstance.timeInMillis = timeInMilliseconds

        return dateFormatter.format(calendarInstance.time)
    }

    fun formatMilliDurationToTime(millis: Long): String = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
        TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
        TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1))

    fun formatDateIntoString(date: Date?, pattern: String): String =
        getDateFormatter(pattern).format(date)

    fun formatStringIntoDate(date: String?, pattern: String): Date =
        getDateFormatter(pattern).parse(date)

    fun switchDateFormats(date: String?, formatOne: String, formatTwo: String): String =
        formatDateIntoString(
            formatStringIntoDate(date, formatOne),
            formatTwo
        )

    fun getTimeDifferenceInDays(startDate: String?, endDate: String?, pattern: String): Long {
        val calendarStartDate = Calendar.getInstance()
        val calendarEndDate = Calendar.getInstance()
        calendarStartDate.time = formatStringIntoDate(startDate, pattern)
        calendarEndDate.time = formatStringIntoDate(endDate, pattern)
        val timeDifference = calendarEndDate.timeInMillis - calendarStartDate.timeInMillis
        return ((timeDifference / (1000 * 60 * 60 * 24)).toDouble().roundToLong() + 1)
    }

    fun getTimeDifferenceToday(dateTarget: String?, pattern: String): Long {
        val calendarTargetDate = Calendar.getInstance()
        val calendarTodayDate = Calendar.getInstance()
        calendarTargetDate.time = formatStringIntoDate(dateTarget, pattern)
        val timeDifference = calendarTodayDate.timeInMillis - calendarTargetDate.timeInMillis
        if (timeDifference < 0) return -1
        return ((timeDifference / (1000 * 60 * 60 * 24)).toDouble().roundToLong() + 1)
    }

    fun getTimeDifferenceToday(dateTarget: Long): Long {
        val calendarTodayDate = Calendar.getInstance()

        return calendarTodayDate.timeInMillis - dateTarget
    }

    fun getYears(time: Long): Int {
        val calendar = Calendar.getInstance()
        val diffInMilliSeconds = time - calendar.timeInMillis
        calendar.timeInMillis = diffInMilliSeconds

        return calendar.get(Calendar.YEAR)
    }

    fun getTodaysDateAsString(pattern: String): String {
        val calendarInstance = Calendar.getInstance()
        calendarInstance.set(Calendar.HOUR, 0)
        calendarInstance.set(Calendar.MINUTE, 0)
        calendarInstance.set(Calendar.SECOND, 0)

        return formatDateIntoString(calendarInstance.time, pattern)
    }

    fun getSpecificDateInMillis(offSet: Long = 0L): Long =
        Calendar.getInstance().timeInMillis + offSet

    fun getCurrentTimeZoneDifferenceInMillis(): Int =
        TimeZone.getDefault().getOffset(Calendar.getInstance().timeInMillis)

    fun getDurationToHumanTime(duration: Long): String {
        val seconds = (duration / 1000) % 60
        val minutes = ((duration / 1000) / 60) % 60

        return String.format("%02d:%02d", minutes, seconds)
    }
}