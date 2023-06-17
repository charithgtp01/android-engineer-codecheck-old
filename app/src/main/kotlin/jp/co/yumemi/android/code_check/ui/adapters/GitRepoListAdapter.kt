package jp.co.yumemi.android.code_check.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.co.yumemi.android.code_check.databinding.LayoutGitRepoListBinding
import jp.co.yumemi.android.code_check.model.GitHubRepo
import javax.inject.Inject

class GitRepoListAdapter @Inject constructor(private val itemClickListener: OnItemClickListener
) : ListAdapter<GitHubRepo, GitRepoListAdapter.GitRepoListViewHolder>(GitHubRepoDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitRepoListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutGitRepoListBinding.inflate(inflater, parent, false)
        return GitRepoListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GitRepoListViewHolder, position: Int) {
        val gitHubRepo = getItem(position)
        holder.binding.repositoryNameView.text = gitHubRepo.name
        holder.itemView.setOnClickListener{
            itemClickListener.itemClick(gitHubRepo)
        }
    }

    /**
     * On Item Click Listener
     */
    interface OnItemClickListener{
        fun itemClick(item: GitHubRepo)
    }
    inner class GitRepoListViewHolder(val binding: LayoutGitRepoListBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}

