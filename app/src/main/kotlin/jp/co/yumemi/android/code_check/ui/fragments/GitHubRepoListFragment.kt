/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.constants.Constants
import jp.co.yumemi.android.code_check.databinding.FragmentGitHubRepoListBinding
import jp.co.yumemi.android.code_check.interfaces.ErrorDialogButtonClickListener
import jp.co.yumemi.android.code_check.model.GitHubRepo
import jp.co.yumemi.android.code_check.ui.adapters.GitRepoListAdapter
import jp.co.yumemi.android.code_check.ui.views.GitHubRepoViewModel
import jp.co.yumemi.android.code_check.utils.DialogUtils
import jp.co.yumemi.android.code_check.utils.DialogUtils.Companion.showErrorDialog
import jp.co.yumemi.android.code_check.utils.DialogUtils.Companion.showProgressDialog
import kotlinx.coroutines.delay

class GitHubRepoListFragment : Fragment() {
    private lateinit var binding: FragmentGitHubRepoListBinding
    private lateinit var viewModel: GitHubRepoViewModel
    private lateinit var gitRepoListAdapter: GitRepoListAdapter
    private var dialog: Dialog? = null
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
        initiateProgressDialog()
        viewModelObservers()
    }

    /**
     * Live Data Updates
     */
    private fun viewModelObservers() {
        /* If server returns an error it will show in the custom error dialog */
        viewModel.errorMessage.observe(requireActivity()) {
            showErrorDialog(requireContext(), it?.error, object : ErrorDialogButtonClickListener {
                override fun onButtonClick() {

                }

            })
        }

        viewModel.isDialogVisible.observe(requireActivity()) {
            if (it) {
                /* Show dialog when calling the API */
                dialog?.show()
            } else {
                /* Dismiss dialog after updating the data list to recycle view */
                dialog?.dismiss()
            }
        }

        /* Observer to catch list data
        * Update Recycle View Items using Diff Utils
        */
        viewModel.gitHubRepoList.observe(requireActivity()) {
            gitRepoListAdapter.submitList(it)
        }
    }

    /**
     * Progress Dialog Initiation
     */
    private fun initiateProgressDialog() {
        dialog = showProgressDialog(context, context?.getString(R.string.wait))
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

