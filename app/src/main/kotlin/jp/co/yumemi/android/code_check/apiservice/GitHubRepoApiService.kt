package jp.co.yumemi.android.code_check.apiservice

import jp.co.yumemi.android.code_check.constants.Constants.REPOSITORIES_ENDPOINT
import jp.co.yumemi.android.code_check.model.ServerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubRepoApiService {

    /**
     * @param q: String part of the repository name
     * @see https://api.github.com/ to get Free APIs
     */
    @GET(REPOSITORIES_ENDPOINT)
    suspend fun getRepositories(@Query("qr") q: String): Response<ServerResponse>
}
