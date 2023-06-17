/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.views

import android.content.Context
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.model.GitHubRepo
import jp.co.yumemi.android.code_check.model.ServerResponse
import jp.co.yumemi.android.code_check.repository.GitHubRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import javax.inject.Inject

/**
 * TwoFragment で使う
 */

@HiltViewModel
class GitHubRepoViewModel @Inject constructor(
    private val gitHubRepository: GitHubRepository
) : ViewModel() {

    private val _gitHubRepoList = MutableLiveData<List<GitHubRepo>>()
    val gitHubRepoList: LiveData<List<GitHubRepo>> get() = _gitHubRepoList

    /**
     * Get Server Response and Set values to live data
     * @param inputText Pass entered value
     */
    private fun getGitHubRepoList(inputText: String) {
        /* View Model Scope - Coroutine */
        viewModelScope.launch {
            val serverResponse: ServerResponse? =
                gitHubRepository.getRepositoriesFromDataSource(inputText)
            _gitHubRepoList.value = serverResponse?.items
        }
    }

    /**
     * Search View Submit Button Click Event
     */
    fun onEditorAction(editeText: TextView?, actionId: Int): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            getGitHubRepoList(editeText?.text.toString())
            return true
        }
        return false
    }
}
