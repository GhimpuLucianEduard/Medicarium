package com.medicarium.Data.LocalDb

import androidx.room.TypeConverter
import com.medicarium.Data.Enums.BloodType
import com.medicarium.Data.Enums.Gender
import empty

class Converters {

    @TypeConverter
    fun fromBloodType(value: BloodType): String {

        if (value == null) {
            return String.empty()
        }

        return value.toString()
    }

    @TypeConverter
    fun toBloodType(value: String): BloodType {
        return BloodType.valueOf(value)
    }

    @TypeConverter
    fun fromGender(value: Gender): String {

        if (value == null) {
            return String.empty()
        }

        return value.toString()
    }

    @TypeConverter
    fun toGender(value: String): Gender {
        return Gender.valueOf(value)
    }
}