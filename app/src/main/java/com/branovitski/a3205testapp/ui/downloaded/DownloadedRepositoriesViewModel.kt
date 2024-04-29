package com.branovitski.a3205testapp.ui.downloaded

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.branovitski.a3205testapp.domain.model.Downloads
import com.branovitski.a3205testapp.domain.model.Repository
import com.branovitski.a3205testapp.domain.repository.RoomRepository
import com.branovitski.a3205testapp.ui.RepositoryListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DownloadedRepositoriesViewModel @Inject constructor(
    private val roomRepository: RoomRepository
) : ViewModel() {

    val downloadedRepositoriesLiveData = MutableLiveData<List<RepositoryListItem>>()

    init {
        viewModelScope.launch {
            roomRepository.getDownloadedRepositories().collectLatest {
                val repositoriesListItems = it.map { downloads ->
                    downloads.toRepositoryListItem()
                }
                downloadedRepositoriesLiveData.postValue(repositoriesListItems)
            }
        }
    }

    private fun Downloads.toRepositoryListItem() = RepositoryListItem(
        name = this.name,
        ownerName = this.login,
        url = this.url,
        language = this.language
    )


}