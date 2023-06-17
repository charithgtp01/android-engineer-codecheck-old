package jp.co.yumemi.android.code_check.repository

import jp.co.yumemi.android.code_check.apiservice.GitHubRepoApiService
import jp.co.yumemi.android.code_check.model.ServerResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GitHubRepository @Inject constructor(
    private val gitHubRepoApiService: GitHubRepoApiService
) {

    /**
     * Coroutines
     */
    suspend fun getRepositoriesFromDataSource(
        value: String
    ): ServerResponse? {
        return withContext(Dispatchers.IO) {
            return@withContext getResponseFromRemoteService(value)
        }
    }

    /**
     * @param  value: String search view text
     * @return ServerResponse Object
     */
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
