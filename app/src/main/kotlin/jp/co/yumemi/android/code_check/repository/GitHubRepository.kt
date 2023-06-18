package jp.co.yumemi.android.code_check.repository

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import jp.co.yumemi.android.code_check.apiservice.GitHubRepoApiService
import jp.co.yumemi.android.code_check.constants.Constants
import jp.co.yumemi.android.code_check.model.Error
import jp.co.yumemi.android.code_check.model.ErrorResponse
import jp.co.yumemi.android.code_check.model.Resource
import jp.co.yumemi.android.code_check.model.Resource.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Repository class to pass data to View Model
 */
class GitHubRepository @Inject constructor(
    context: Context,
    private val gitHubRepoApiService: GitHubRepoApiService
) {
    val context: Context = context

    /**
     * Coroutines
     */
    suspend fun getRepositoriesFromDataSource(
        value: String
    ): Resource? {
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
    ): Resource? {

        /* Get Server Response */
        val response = gitHubRepoApiService.getRepositories(value)
        return if (response.isSuccessful) {
            Success(data = response.body()!!)
        } else {
            Log.d(Constants.TAG,response.errorBody().toString())
            val gson = Gson()
            //Deserialize error response.body
            val type = object : TypeToken<Error>() {}.type
            val errorObject: Error = gson.fromJson(response.errorBody()?.charStream(), type)

            Resource.Error(
                ErrorResponse(
                    errorObject.message,
                    response.code()
                )
            )
        }
    }
}
