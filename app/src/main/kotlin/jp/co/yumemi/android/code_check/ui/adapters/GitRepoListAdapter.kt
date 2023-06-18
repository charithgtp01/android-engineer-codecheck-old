package jp.co.yumemi.android.code_check.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import jp.co.yumemi.android.code_check.databinding.LayoutGitRepoListBinding
import jp.co.yumemi.android.code_check.model.GitHubRepo
import javax.inject.Inject

class GitRepoListAdapter @Inject constructor(
    private val itemClickListener: OnItemClickListener
) : ListAdapter<GitHubRepo, GitRepoListAdapter.GitRepoListViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitRepoListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutGitRepoListBinding.inflate(inflater, parent, false)
        return GitRepoListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GitRepoListViewHolder, position: Int) {
        val gitHubRepo = getItem(position)
        holder.binding.repositoryNameView.text = gitHubRepo.name
        /* Show profile icon using Glide */
        Glide.with(holder.itemView.rootView).load(gitHubRepo.owner?.avatarUrl)
            .into(holder.binding.ownerIconView)
        holder.itemView.setOnClickListener {
            itemClickListener.itemClick(gitHubRepo)
        }
    }

    /**
     * On Item Click Listener
     */
    interface OnItemClickListener {
        fun itemClick(item: GitHubRepo)
    }

    inner class GitRepoListViewHolder(val binding: LayoutGitRepoListBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}

/**
 * Diff Util Interface
 */
val diffUtil = object : DiffUtil.ItemCallback<GitHubRepo>() {
    override fun areItemsTheSame(oldItem: GitHubRepo, newItem: GitHubRepo): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: GitHubRepo, newItem: GitHubRepo): Boolean {
        return oldItem == newItem
    }
}

