package com.branovitski.a3205testapp.ui.search

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.branovitski.a3205testapp.R
import com.branovitski.a3205testapp.databinding.SearchFragmentBinding
import com.branovitski.a3205testapp.domain.model.Repository
import com.branovitski.a3205testapp.ui.RepositoryAdapter
import com.branovitski.a3205testapp.ui.RepositoryListItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.search_fragment) {

    private val binding by viewBinding(SearchFragmentBinding::bind)
    private val viewModel by viewModels<SearchViewModel>()

    private var repositoryAdapter: RepositoryAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRepositoryList()

        with(binding) {
            searchView.queryHint = "Search"

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText == "") {
                        viewModel.queryFlow.value = ""
                    } else {
                        viewModel.queryFlow.value = newText.toString()
                    }
                    return true
                }
            })
        }
    }

    private fun setupRepositoryList() {
        repositoryAdapter = RepositoryAdapter().apply {
            onItemClick = {
                openRepositoryInBrowser(it.url)
            }

            onDownloadItemClick = {
                downloadFile(it)
            }
        }

        binding.repositoriesList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.repositoriesList.adapter = repositoryAdapter

        lifecycleScope.launch {
            viewModel.repositoryListItemsFlow.collectLatest {
                repositoryAdapter?.submitList(it)
            }
        }

    }

    private fun downloadFile(repository: RepositoryListItem) {
        viewModel.downloadRepository(repository)
        viewModel.downloadingFileResponse.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                Downloader(
                    requireActivity(),
                    it,
                    repository.name,
                    onComplete = {
                        viewModel.addToDownloads(repository)
                    }
                ).downloadFile()
            }
        }
    }

    private fun openRepositoryInBrowser(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

}