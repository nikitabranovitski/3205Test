package com.branovitski.a3205testapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.branovitski.a3205testapp.databinding.RepositoryListItemBinding

class RepositoryAdapter :
    ListAdapter<RepositoryListItem, RepositoryAdapter.RepositoryViewHolder>(RepositoryDiffCallback()) {

    lateinit var onItemClick: (repository: RepositoryListItem) -> Unit
    lateinit var onDownloadItemClick: (repository: RepositoryListItem) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = RepositoryListItemBinding.inflate(inflater, parent, false)
        return RepositoryViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RepositoryViewHolder(private val binding: RepositoryListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: RepositoryListItem) {

            itemView.setOnClickListener {
                onItemClick(item)
            }

            binding.downloadIcon.setOnClickListener {
                onDownloadItemClick(item)
            }

            binding.nameTextView.text = item.name
            binding.languageTextView.text = item.language

        }

    }
}

private class RepositoryDiffCallback : DiffUtil.ItemCallback<RepositoryListItem>() {

    override fun areItemsTheSame(oldItem: RepositoryListItem, newItem: RepositoryListItem) =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: RepositoryListItem, newItem: RepositoryListItem) =
        oldItem == newItem
}