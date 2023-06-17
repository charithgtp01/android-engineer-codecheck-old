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
import jp.co.yumemi.android.code_check.databinding.FragmentGitHubRepoListBinding
import jp.co.yumemi.android.code_check.model.GitHubRepo
import jp.co.yumemi.android.code_check.ui.adapters.GitRepoListAdapter
import jp.co.yumemi.android.code_check.ui.views.GitHubRepoViewModel

class GitHubRepoListFragment : Fragment() {
    private lateinit var binding: FragmentGitHubRepoListBinding
    private lateinit var viewModel: GitHubRepoViewModel
    private lateinit var gitRepoListAdapter: GitRepoListAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /*
         * Initiate Data Binding and View Model
        */
        binding = FragmentGitHubRepoListBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[GitHubRepoViewModel::class.java]
        binding.vm = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initiateAdapter()
    }

    /**
     * Recycle View data configuration
     */
    private fun initiateAdapter() {
        /* Initiate Adapter */
        gitRepoListAdapter = GitRepoListAdapter(object : GitRepoListAdapter.OnItemClickListener {
            override fun itemClick(item: GitHubRepo) {
                gotoRepositoryFragment(item)
            }
        })

        /* Set Adapter to Recycle View */
        binding.recyclerView.also { it2 ->
            it2.adapter = gitRepoListAdapter

            /* Observer to catch list data
            * Update Recycle View Items using Diff Utils
            */
            viewModel.gitHubRepoList.observe(requireActivity()) {
                gitRepoListAdapter.submitList(it)
            }

        }
    }

    /**
     * Navigate to Next Fragment Using Navigation Controller
     * Pass selected Git Hub Repo Object using Safe Args
     */
    fun gotoRepositoryFragment(gitHubRepo: GitHubRepo) {
        findNavController().navigate(
            GitHubRepoListFragmentDirections.actionRepositoriesFragmentToRepositoryFragment(
                gitHubRepo
            )
        )
    }

}

