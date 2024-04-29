package com.branovitski.a3205testapp.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.branovitski.a3205testapp.domain.model.Downloads
import com.branovitski.a3205testapp.domain.model.Repository
import com.branovitski.a3205testapp.domain.repository.ApiRepository
import com.branovitski.a3205testapp.domain.repository.RoomRepository
import com.branovitski.a3205testapp.ui.RepositoryListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: ApiRepository,
    private val roomRepository: RoomRepository
) : ViewModel() {

    val queryFlow = MutableStateFlow(value = "nikitabranovitski")

    val repositoryListItemsFlow =
        queryFlow.debounce(300).flatMapLatest { query ->
            repository.getRepositories(query)
        }.map { list ->
            list.map {
                it.toRepositoryListItem()
            }
        }.catch {
            emit(listOf())
        }

    val downloadingFileResponse = MutableLiveData<ResponseBody>()

    fun downloadRepository(repo: RepositoryListItem) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.downloadRepository(repo.ownerName, repo.name)
                    .collect {
                        downloadingFileResponse.postValue(it)
                    }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addToDownloads(repository: RepositoryListItem) {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.addRepositoryToDownload(repository.toDownloads())
        }
    }

    private fun RepositoryListItem.toDownloads() = Downloads(
        name = this.name,
        login = this.ownerName,
        url = this.url,
        language = this.language
    )

    private fun Repository.toRepositoryListItem() = RepositoryListItem(
        name = this.name,
        ownerName = this.owner.login,
        url = this.html_url,
        language = this.language.toString()
    )
}