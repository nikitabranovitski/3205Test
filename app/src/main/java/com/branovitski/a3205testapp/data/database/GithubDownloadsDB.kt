package com.branovitski.a3205testapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.branovitski.a3205testapp.domain.model.Downloads

@Database(entities = [Downloads::class], version = 1, exportSchema = false)
abstract class GithubDownloadsDB : RoomDatabase() {
    abstract fun githubClientDao(): GithubDownloadsDAO

    companion object {

        fun getDatabase(context: Context): GithubDownloadsDB {
            return Room.databaseBuilder(
                context.applicationContext,
                GithubDownloadsDB::class.java,
                "downloads_table"
            )
                .allowMainThreadQueries()
                .build()
        }
    }
}