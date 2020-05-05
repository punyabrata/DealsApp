package com.target.dealbrowserpoc.dealbrowser.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.target.dealbrowserpoc.dealbrowser.db.dao.DealDao
import com.target.dealbrowserpoc.dealbrowser.entity.Datum

@Database(entities = [Datum::class], version = 1, exportSchema = false)
abstract class DealDatabase : RoomDatabase() {
    abstract fun dealDao(): DealDao

    companion object {
        @Volatile private var instance: DealDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context) = instance ?: synchronized(lock) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context.applicationContext,
                DealDatabase::class.java,
                "deal_database")
                .fallbackToDestructiveMigration()
                .build()
    }
}