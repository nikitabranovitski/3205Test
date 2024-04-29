package com.branovitski.a3205testapp.domain.repository

import com.branovitski.a3205testapp.domain.model.Downloads
import kotlinx.coroutines.flow.Flow

interface RoomRepository {

   suspend fun addRepositoryToDownload(downloadedRepository: Downloads)

   suspend fun getDownloadedRepositories(): Flow<List<Downloads>>

}