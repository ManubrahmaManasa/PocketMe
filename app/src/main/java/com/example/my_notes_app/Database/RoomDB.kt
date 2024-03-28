package com.example.my_notes_app.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.example.my_notes_app.Models.Notes

@Database(entities = [Notes::class], version = 1, exportSchema = false)
abstract class RoomDB : RoomDatabase() {
    abstract fun mainDAO(): MainDAO?

    companion object {
        private var dataBase: RoomDB? = null
        private const val DATABASE_NAME = "NotesApp"
        @JvmStatic
        @Synchronized
        fun getInstance(context: Context): RoomDB? {
            if (dataBase == null) {
                dataBase = databaseBuilder(
                    context.applicationContext,
                    RoomDB::class.java, DATABASE_NAME
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return dataBase
        }
    }
}
