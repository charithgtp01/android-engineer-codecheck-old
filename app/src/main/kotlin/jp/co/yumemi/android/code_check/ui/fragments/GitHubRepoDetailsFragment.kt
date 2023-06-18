/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import jp.co.yumemi.android.code_check.databinding.FragmentGitHubDetailsBinding
import jp.co.yumemi.android.code_check.model.GitHubRepo
import jp.co.yumemi.android.code_check.ui.views.GitHubRepoDetailsViewModel

/**
 * Details Page Fragment
 */
class GitHubRepoDetailsFragment : Fragment() {
    private val args: GitHubRepoDetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentGitHubDetailsBinding
    private lateinit var viewModel: GitHubRepoDetailsViewModel
    private lateinit var gitHubRepo: GitHubRepo

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /* Safe Args */
        gitHubRepo = args.item

        /*
         * Initiate Data Binding and View Model
         * Retrieve the Github Object from GitHubRepoListFragment
         */
        binding = FragmentGitHubDetailsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[GitHubRepoDetailsViewModel::class.java]
        binding.vm = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
    }

    /**
     * Pass selected Git Repo Object to view model
     * Set data to view
     * @see https://bumptech.github.io/glide/
     */
    private fun setData() {
        viewModel.setGitRepoData(gitHubRepo)

        /* Show profile icon using Glide */
        Glide.with(this).load(gitHubRepo.owner?.avatarUrl).into(binding.ownerIconView)
    }


}
