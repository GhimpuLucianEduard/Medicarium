package com.medicarium.Data.LocalDb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.commonsware.cwac.saferoom.SafeHelperFactory
import com.medicarium.Data.Models.User

@Database(
    entities = [User::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile private var instance: AppDatabase? = null
        private var LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "medicarium.db")
                .openHelperFactory(SafeHelperFactory(charArrayOf('m','e','d','i','c','a','r','i','u','m'))).build()

    }
}