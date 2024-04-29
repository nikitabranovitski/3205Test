package com.branovitski.a3205testapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.branovitski.a3205testapp.domain.model.Downloads
import kotlinx.coroutines.flow.Flow

@Dao
interface GithubDownloadsDAO {
    @Query("SELECT * FROM downloads")
    suspend fun getDownloadedRepositories(): List<Downloads>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRepository(downloadRepositoryEntity: Downloads)
}