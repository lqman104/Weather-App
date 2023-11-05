package com.luqman.weather.core.helper

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateHelper {

    private const val SIMPLE_DATE = "dd-MM-yyyy"
    private const val SIMPLE_DATE_VARIANT = "yyyy-MM-dd"
    private const val SIMPLE_TIME = "HH:mm"
    const val HOUR = "HH"

    fun Long?.toDate(toFormat: String = SIMPLE_DATE): String {
        return if (this == null || this == 0L) {
            ""
        } else {
            val simpleDateFormat = SimpleDateFormat(toFormat, Locale.getDefault())
            val date = Date(this)
            simpleDateFormat.format(date)
        }
    }

    fun currentDate(toFormat: String = SIMPLE_DATE_VARIANT): String {
        val time = Calendar.getInstance().time
        val simpleDateFormat = SimpleDateFormat(toFormat, Locale.getDefault())
        return simpleDateFormat.format(time)
    }

    fun currentTime(toFormat: String = SIMPLE_TIME): String {
        val time = Calendar.getInstance().time
        val simpleDateFormat = SimpleDateFormat(toFormat, Locale.getDefault())
        return simpleDateFormat.format(time)
    }

    fun String.getIntHours(): Int {
        val inputFormatter = SimpleDateFormat(SIMPLE_TIME, Locale.getDefault())
        val outputFormatter = SimpleDateFormat(HOUR, Locale.getDefault())

        return try {
            val parsedDate = inputFormatter.parse(this)
            if (parsedDate != null) {
                outputFormatter.format(parsedDate).toInt()
            } else {
                -1
            }
        } catch (e: Exception) {
            e.printStackTrace()
            -1
        }
    }
}