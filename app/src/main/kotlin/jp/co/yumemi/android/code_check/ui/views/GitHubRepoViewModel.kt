/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.views

import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.model.GitHubRepo
import jp.co.yumemi.android.code_check.model.ServerResponse
import jp.co.yumemi.android.code_check.repository.GitHubRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

    private val _isDialogVisible = MutableLiveData<Boolean>()

    val errorMessage = MutableLiveData<String>()
    val isDialogVisible: LiveData<Boolean> get() = _isDialogVisible

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

            /* Hide Progress Dialog with 1 Second delay after fetching the data list from the server */
            delay(1000L)
            _isDialogVisible.value = false
        }
    }

    /**
     * Search View Submit Button Click Event
     */
    fun onEditorAction(editeText: TextView?, actionId: Int): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            //Show Progress Dialog when click on the search view submit button
            _isDialogVisible.value = true
            getGitHubRepoList(editeText?.text.toString())
            return true
        }
        return false
    }
}
