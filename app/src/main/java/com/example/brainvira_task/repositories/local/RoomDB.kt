package com.example.brainvira_task.repositories.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.brainvira_task.model.SearchItemDetailsResponse
@Database(
    entities = [
    SearchItemDetailsResponse::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(

)
abstract class RoomDB : RoomDatabase() {
    abstract fun roomDao(): RoomDao

    companion object {
        val MIGRATION_1_2: Migration =
            object : Migration(1, 2) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    // Since we didn't alter the table, there's nothing else to do here.
                }
            }

        @Volatile
        private var INSTANCE: RoomDB? = null

        fun getDatabase(context: Context): RoomDB =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(ctx: Context) =
            Room.databaseBuilder(ctx.applicationContext, RoomDB::class.java, "axxess.db")
                .addMigrations(MIGRATION_1_2)
                .build()
    }
}