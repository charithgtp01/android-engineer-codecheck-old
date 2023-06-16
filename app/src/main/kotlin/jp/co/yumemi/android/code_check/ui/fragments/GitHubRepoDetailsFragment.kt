/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.databinding.FragmentGitHubDetailsBinding

class GitHubRepoDetailsFragment : Fragment(R.layout.fragment_git_hub_details) {

    private val args: GitHubRepoDetailsFragmentArgs by navArgs()

    private var binding: FragmentGitHubDetailsBinding? = null
    private val _binding get() = binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        Log.d("検索した日時", lastSearchDate.toString())

        binding = FragmentGitHubDetailsBinding.bind(view)

        var item = args.item

        _binding.ownerIconView.load(item.owner.avatarUrl);
        _binding.nameView.text = item.name;
        _binding.languageView.text = item.language;
        _binding.starsView.text = "${item.stargazersCount} stars";
        _binding.watchersView.text = "${item.watchersCount} watchers";
        _binding.forksView.text = "${item.forksCount} forks";
        _binding.openIssuesView.text = "${item.openIssuesCount} open issues";
    }
}
