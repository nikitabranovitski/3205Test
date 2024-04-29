package com.branovitski.a3205testapp.di

import com.branovitski.a3205testapp.data.api.GitHubApi
import com.branovitski.a3205testapp.data.database.GithubDownloadsDAO
import com.branovitski.a3205testapp.data.repository.ApiRepositoryImpl
import com.branovitski.a3205testapp.data.repository.RoomRepositoryImpl
import com.branovitski.a3205testapp.domain.repository.ApiRepository
import com.branovitski.a3205testapp.domain.repository.RoomRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun provideGitHubRepository(api: GitHubApi): ApiRepository {
        return ApiRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun provideDaoRepository(dao: GithubDownloadsDAO): RoomRepository {
        return RoomRepositoryImpl(dao)
    }
}