package jp.co.yumemi.android.code_check.apiservice

import jp.co.yumemi.android.code_check.constants.Constants.REPOSITORIES_ENDPOINT
import jp.co.yumemi.android.code_check.model.ServerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubRepoApiService {
    @GET(REPOSITORIES_ENDPOINT)
    suspend fun getRepositories(@Query("q") q: String): Response<ServerResponse>
}
