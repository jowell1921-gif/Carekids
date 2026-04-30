package com.example.carekids.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities    = [EmotionalEntry::class, TeamMember::class],
    version     = 2,
    exportSchema = false
)
abstract class CareKidsDatabase : RoomDatabase() {

    abstract fun emotionalDao(): EmotionalDao
    abstract fun teamMemberDao(): TeamMemberDao

    companion object {
        @Volatile
        private var INSTANCE: CareKidsDatabase? = null

        fun getInstance(context: Context): CareKidsDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    CareKidsDatabase::class.java,
                    "carekids_db"
                )
                // En desarrollo, si cambia el esquema se borra y recrea la BD.
                .fallbackToDestructiveMigration()
                .build()
                .also { INSTANCE = it }
            }
        }
    }
}
