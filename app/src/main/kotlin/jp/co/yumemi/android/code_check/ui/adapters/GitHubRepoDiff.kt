package jp.co.yumemi.android.code_check.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import jp.co.yumemi.android.code_check.model.GitHubRepo

class GitHubRepoDiff : DiffUtil.ItemCallback<GitHubRepo>() {
    override fun areItemsTheSame(oldItem: GitHubRepo, newItem: GitHubRepo): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: GitHubRepo, newItem: GitHubRepo): Boolean {
        return oldItem == newItem
    }
}