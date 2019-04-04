package com.medicarium.Utilities

import java.util.*

class DateTimeUtility {
    companion object {
        fun getCurrentDateInMs(): Long {
            return Calendar.getInstance().timeInMillis
        }
    }
}