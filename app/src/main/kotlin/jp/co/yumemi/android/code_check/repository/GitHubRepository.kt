package jp.co.yumemi.android.code_check.repository

import jp.co.yumemi.android.code_check.apiservice.GitHubRepoApiService
import jp.co.yumemi.android.code_check.model.ServerResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GitHubRepository @Inject constructor(
    private val gitHubRepoApiService: GitHubRepoApiService
) {
    suspend fun getRepositoriesFromDataSource(
        dealerCode: String
    ): ServerResponse? {
        return withContext(Dispatchers.IO) {
            return@withContext getResponseFromRemoteService(dealerCode)
        }
    }

    //From remote data source
    private suspend fun getResponseFromRemoteService(
        value: String
    ): ServerResponse? {
        val response = gitHubRepoApiService.getRepositories(value)
        return if (response.isSuccessful) {
            response.body()
            //            response.body()?.let { responseListener.onSuccess(it) }
        } else {
            null
        }
//            responseListener.onError(response.errorBody().toString())
    }
}
