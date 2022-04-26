package com.amod.kotlinquotes.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Quote::class), version = 1, exportSchema = false)
abstract class QuoteDatabase : RoomDatabase() {

    abstract fun getQuoteDao(): QuoteDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: QuoteDatabase? = null

        fun getDatabase(context: Context): QuoteDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuoteDatabase::class.java,
                    "quote_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }


}