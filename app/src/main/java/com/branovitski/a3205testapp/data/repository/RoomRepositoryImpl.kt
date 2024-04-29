package com.branovitski.a3205testapp.data.repository

import com.branovitski.a3205testapp.data.database.GithubDownloadsDAO
import com.branovitski.a3205testapp.domain.model.Downloads
import com.branovitski.a3205testapp.domain.repository.RoomRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(private val dao: GithubDownloadsDAO) : RoomRepository {

    override suspend fun addRepositoryToDownload(downloadedRepository: Downloads) =
        dao.addRepository(downloadedRepository)

    override suspend fun getDownloadedRepositories() = flow {
        emit(dao.getDownloadedRepositories())
    }
}