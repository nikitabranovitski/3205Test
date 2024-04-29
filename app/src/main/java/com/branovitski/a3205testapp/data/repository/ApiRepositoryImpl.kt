package com.branovitski.a3205testapp.data.repository

import com.branovitski.a3205testapp.data.api.GitHubApi
import com.branovitski.a3205testapp.domain.model.Repository
import com.branovitski.a3205testapp.domain.repository.ApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(private val api: GitHubApi) : ApiRepository {

    override suspend fun getRepositories(query: String): Flow<List<Repository>> =
        flow {
            emit(api.getSearchRepositories(query))
        }

    override suspend fun downloadRepository(owner: String, repo: String) = flow {
        emit(api.downloadRepository(owner, repo))
    }


}