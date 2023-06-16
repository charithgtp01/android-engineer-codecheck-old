/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.databinding.FragmentGitHubRepoListBinding
import jp.co.yumemi.android.code_check.model.GitHubRepo
import jp.co.yumemi.android.code_check.ui.adapters.GitRepoListAdapter
import jp.co.yumemi.android.code_check.ui.views.GitHubRepoViewModel

class GitHubRepoListFragment : Fragment() {

    lateinit var binding: FragmentGitHubRepoListBinding
    private lateinit var viewModel: GitHubRepoViewModel
    private lateinit var gitRepoListAdapter: GitRepoListAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGitHubRepoListBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[GitHubRepoViewModel::class.java]
        binding.vm = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gitRepoListAdapter = GitRepoListAdapter(object : GitRepoListAdapter.OnItemClickListener {
            override fun itemClick(item: GitHubRepo) {
                gotoRepositoryFragment(item)
            }
        })

        binding.searchInputText
            .setOnEditorActionListener { editText, action, _ ->
                if (action == EditorInfo.IME_ACTION_SEARCH) {
                    editText.text.toString().let {
                        viewModel.searchResults(it)
                    }
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

        binding.recyclerView.also {
            it.adapter = gitRepoListAdapter
            viewModel.gitHubRepoList.observe(requireActivity()) { it ->
                gitRepoListAdapter.submitList(it)
            }

        }
    }

    fun gotoRepositoryFragment(item: GitHubRepo) {
        val _action =
            GitHubRepoListFragmentDirections.actionRepositoriesFragmentToRepositoryFragment(item = item)
        findNavController().navigate(_action)
    }
}

val diff_util = object : DiffUtil.ItemCallback<GitHubRepo>() {
    override fun areItemsTheSame(oldItem: GitHubRepo, newItem: GitHubRepo): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: GitHubRepo, newItem: GitHubRepo): Boolean {
        return oldItem == newItem
    }

}

