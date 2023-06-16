/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.fragments

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.databinding.FragmentGitHubRepoListBinding
import jp.co.yumemi.android.code_check.model.GitHubRepo
import jp.co.yumemi.android.code_check.ui.adapters.GitRepoListAdapter
import jp.co.yumemi.android.code_check.ui.views.OneViewModel

class GitHubRepoListFragment: Fragment(R.layout.fragment_git_hub_repo_list){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        val _binding= FragmentGitHubRepoListBinding.bind(view)

        val _viewModel= OneViewModel(requireContext())

        val _layoutManager= LinearLayoutManager(requireContext())
        val _dividerItemDecoration=
            DividerItemDecoration(requireContext(), _layoutManager.orientation)
        val _adapter= GitRepoListAdapter(object : GitRepoListAdapter.OnItemClickListener {
            override fun itemClick(item: GitHubRepo){
                gotoRepositoryFragment(item)
            }
        })

        _binding.searchInputText
            .setOnEditorActionListener{ editText, action, _ ->
                if (action== EditorInfo.IME_ACTION_SEARCH){
                    editText.text.toString().let {
                        _viewModel.searchResults(it).apply{
                            _adapter.submitList(this)
                        }
                    }
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

        _binding.recyclerView.also{
            it.layoutManager= _layoutManager
            it.addItemDecoration(_dividerItemDecoration)
            it.adapter= _adapter
        }
    }

    fun gotoRepositoryFragment(item: GitHubRepo)
    {
        val _action=
            GitHubRepoListFragmentDirections.actionRepositoriesFragmentToRepositoryFragment(item = item)
        findNavController().navigate(_action)
    }
}

val diff_util= object: DiffUtil.ItemCallback<GitHubRepo>(){
    override fun areItemsTheSame(oldItem: GitHubRepo, newItem: GitHubRepo): Boolean
    {
        return oldItem.name== newItem.name
    }

    override fun areContentsTheSame(oldItem: GitHubRepo, newItem: GitHubRepo): Boolean
    {
        return oldItem== newItem
    }

}

