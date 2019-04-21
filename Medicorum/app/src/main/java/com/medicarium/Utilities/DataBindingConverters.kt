package com.medicarium.Utilities

import androidx.databinding.InverseMethod
import com.google.android.material.textfield.TextInputEditText
import com.medicarium.Data.Enums.MedicalCategory
import empty
import timestampToString
import java.util.*

object TimestampStringConverter {

    @InverseMethod("stringToTimestamp")
    @JvmStatic fun timestampToString(
        timestamp: Long?
    ): String {

        return timestamp.timestampToString()
    }

    @JvmStatic fun stringToTimestamp(
        text: String
    ): Long? {
        // TODO
        return text.toLong()
    }
}

object MedicalCategoryStringConverter {

    @InverseMethod("stringToMedicalCategory")
    @JvmStatic fun medicalCategoryToString(
        medicalCategory: MedicalCategory?
    ): String {

        if (medicalCategory == null) {
            return String.empty()
        }

        return medicalCategory.toString()
    }

    @JvmStatic fun stringToMedicalCategory(
        text: String
    ): MedicalCategory? {
        // TODO
        return MedicalCategory.valueOf(text)
    }
}

