package com.vinay.myapplication.local

import androidx.room.*
import android.content.Context
import com.vinay.myapplication.data.remote.model.TestModel
import com.vinay.myapplication.local.DbConstants.DATABASE_FALLBACK_VERSION
import com.vinay.myapplication.local.DbConstants.DATABASE_NAME

@Database(
    version = 1,
    // model entries
    entities = arrayOf(
        TestModel::class
    )
)
abstract class AppDatabase : RoomDatabase() {


    companion object {

        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigrationFrom(DATABASE_FALLBACK_VERSION)
                    .fallbackToDestructiveMigration().build()

            }
            return INSTANCE as AppDatabase
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}