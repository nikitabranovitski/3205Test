package com.branovitski.a3205testapp.ui.downloaded

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.branovitski.a3205testapp.R
import com.branovitski.a3205testapp.databinding.DownloadedRepositoriesFragmentBinding
import com.branovitski.a3205testapp.ui.RepositoryAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DownloadedRepositoriesFragment : Fragment(R.layout.downloaded_repositories_fragment) {

    private val binding by viewBinding(DownloadedRepositoriesFragmentBinding::bind)
    private val viewModel by viewModels<DownloadedRepositoriesViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRepositoryList()

    }

    private fun setupRepositoryList() {
        val repositoryAdapter = RepositoryAdapter().apply {
            onItemClick = {
                openRepositoryInBrowser(it.url)
            }

            onDownloadItemClick = {
                Toast.makeText(requireContext(), "Already downloaded", Toast.LENGTH_SHORT).show()
            }
        }

        binding.repositoriesList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.repositoriesList.adapter = repositoryAdapter
        viewModel.downloadedRepositoriesLiveData.observe(viewLifecycleOwner) {
            repositoryAdapter.submitList(it)
        }

    }

    private fun openRepositoryInBrowser(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }
}