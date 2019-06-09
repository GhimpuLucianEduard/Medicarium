package com.medicarium.Utilities

import androidx.databinding.InverseMethod
import androidx.room.TypeConverter
import com.google.android.material.textfield.TextInputEditText
import com.medicarium.Data.Enums.BloodType
import com.medicarium.Data.Enums.Gender
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

object BloodTypeStringConverter {

    @TypeConverter
    @InverseMethod("stringToBloodType")
    @JvmStatic fun bloodTypeToString(
        bloodType: BloodType?
    ): String {

        if (bloodType == null) {
            return String.empty()
        }

        return bloodType.toString()
    }

    @TypeConverter
    @JvmStatic fun stringToBloodType(
        text: String
    ): BloodType? {
        // TODO
        return BloodType.valueOf(text)
    }
}

object GendreStringConverter {

    @InverseMethod("stringToGendre")
    @JvmStatic fun gendreToString(
        gendre: Gender?
    ): String {

        if (gendre == null) {
            return String.empty()
        }

        return gendre.toString()
    }

    @JvmStatic fun stringToGendre(
        text: String
    ): Gender? {
        // TODO
        return Gender.valueOf(text)
    }
}
