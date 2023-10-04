package com.example.gameapp.data

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentWeekDates(): String {

        val now = LocalDate.now()
        val dayOfWeek = now.dayOfWeek
        val startDate = now.minusDays(dayOfWeek.value.toLong() - 1)
        val endDate = startDate.plusDays(6)

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formattedStartDate = startDate.format(formatter)
        val formattedEndDate = endDate.format(formatter)

        return "$formattedStartDate,$formattedEndDate"
    }

