package com.agileengine.test.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.agileengine.test.BuildConfig
import com.agileengine.test.models.Repo


@Database(entities = [Repo::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun repoDao(): IRepoDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(constext: Context): AppDatabase {
            val dbName = BuildConfig.DB_NAME
            return Room.databaseBuilder(constext, AppDatabase::class.java, dbName)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}