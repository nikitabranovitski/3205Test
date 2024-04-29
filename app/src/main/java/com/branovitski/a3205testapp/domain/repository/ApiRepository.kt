package com.branovitski.a3205testapp.domain.repository

import com.branovitski.a3205testapp.domain.model.Repository
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody

interface ApiRepository {
    suspend fun getRepositories(query: String): Flow<List<Repository>>

    suspend fun downloadRepository(owner: String, repo: String): Flow<ResponseBody>
}