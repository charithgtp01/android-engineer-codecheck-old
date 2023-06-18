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
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.model.GitHubRepo
import jp.co.yumemi.android.code_check.repository.GitHubRepository
import jp.co.yumemi.android.code_check.utils.Utils.Companion.isOnline
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Home Fragment View Model
 */
@HiltViewModel
class GitHubRepoViewModel @Inject constructor(
    private val gitHubRepository: GitHubRepository
) : ViewModel() {

    private val _gitHubRepoList = MutableLiveData<List<GitHubRepo>>()
    val gitHubRepoList: LiveData<List<GitHubRepo>> get() = _gitHubRepoList

    //Dialog Visibility Live Data
    private val _isDialogVisible = MutableLiveData<Boolean>()
    val isDialogVisible: LiveData<Boolean> get() = _isDialogVisible

    //Error Message Live Data
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    /**
     * Get Server Response and Set values to live data
     * @param inputText Pass entered value
     */
    private fun getGitHubRepoList(inputText: String) {
        /* View Model Scope - Coroutine */
        viewModelScope.launch {
            val resource = gitHubRepository.getRepositoriesFromDataSource(inputText)

            if (resource?.data != null) {
                _gitHubRepoList.value = resource.data.items
            } else
                _errorMessage.value = resource?.error?.error

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

            val enteredValue = editeText?.text.toString()

            if (enteredValue.isNullOrBlank()) {
                //Empty value error Alert
                _errorMessage.value = gitHubRepository.context.getString(R.string.no_internet)
            } else{
                val isNetworkAvailable = isOnline(gitHubRepository.context.applicationContext)

                //If Network available call to backend API
                if (isNetworkAvailable) {
                    //Show Progress Dialog when click on the search view submit button
                    _isDialogVisible.value = true
                    getGitHubRepoList(editeText?.text.toString())
                } else {
                    //Show Error Alert
                    _errorMessage.value = gitHubRepository.context.getString(R.string.no_internet)
                }
            }


        return true
    }
    return false
}
}
