package com.branovitski.a3205testapp.di

import android.app.Application
import com.branovitski.a3205testapp.data.database.GithubDownloadsDAO
import com.branovitski.a3205testapp.data.database.GithubDownloadsDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun getDatabase(context: Application): GithubDownloadsDB {
        return GithubDownloadsDB.getDatabase(context)
    }

    @Singleton
    @Provides
    fun getDao(app: GithubDownloadsDB): GithubDownloadsDAO {
        return app.githubClientDao()
    }
}