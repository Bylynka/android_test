package com.agileengine.test.features.repos

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.agileengine.test.R
import com.agileengine.test.common.inflate
import com.agileengine.test.databinding.ListItemRepoBinding
import com.agileengine.test.models.Repo

class ReposAdapter : ListAdapter<Repo, ReposAdapter.RepoViewHolder>(RepoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RepoViewHolder(parent)

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class RepoViewHolder(parent: ViewGroup) :
        RecyclerView.ViewHolder(parent.inflate(R.layout.list_item_repo)) {

        private val binding = ListItemRepoBinding.bind(itemView)

        fun bind(repo: Repo) = binding.run {
            vm = repo
            executePendingBindings()
        }
    }


    class RepoDiffCallback : DiffUtil.ItemCallback<Repo>() {

        override fun areItemsTheSame(oldItem: Repo, newItem: Repo) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
            return oldItem.name == newItem.name && oldItem.description == newItem.description
        }

    }


}