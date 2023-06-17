package jp.co.yumemi.android.code_check.ui.views

import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.model.GitHubRepo
import javax.inject.Inject


@HiltViewModel
class GitHubRepoDetailsViewModel @Inject constructor() : ViewModel() {

    private val _gitRepoData = MutableLiveData<GitHubRepo>(null)
    val gitRepoData: LiveData<GitHubRepo> get() = _gitRepoData

    /**
     * Set Git Hub Object to Live Data
     * @param Selected Git Hub Repo Object
     */
    fun setGitRepoData(gitHubRepo: GitHubRepo) {
        _gitRepoData.value = gitHubRepo
    }

}