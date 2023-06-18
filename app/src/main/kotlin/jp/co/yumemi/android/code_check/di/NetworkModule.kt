package jp.co.yumemi.android.code_check.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.co.yumemi.android.code_check.apiservice.GitHubRepoApiService
import jp.co.yumemi.android.code_check.constants.Constants.BASE_URL
import jp.co.yumemi.android.code_check.repository.GitHubRepository
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Application context
     */
    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext


    /**
     * Get Base Url of the Rest API
     */
    @Singleton
    @Provides
    fun provideBaseUrl(): String {
        return BASE_URL
    }

    /**
     * Create Gson Convertor Factory
     */
    @Singleton
    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create();
    }

    /**
     * Create OkHttpClient
     * Add Interceptor with Headers
     */
    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val okHttpClient =
            OkHttpClient.Builder().addInterceptor(ServiceInterceptor())
        return okHttpClient.build()
    }

    /**
     * Create Retrofit Instance
     */
    @Singleton
    @Provides
    fun provideRetrofit(
        baseUrl: String,
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()
    }

    /**
     *  Abstract the communication with the remote API
     *  Create Api Service Interface
     */
    @Singleton
    @Provides
    fun provideGithubAccountApiService(retrofit: Retrofit): GitHubRepoApiService {
        return retrofit.create(GitHubRepoApiService::class.java)
    }

    /**
     * Create abstraction layer
     * GitHubRepoApiService provide to access remote data
     */
    @Singleton
    @Provides
    fun provideGithubAccountRepository(application: Application,gitHubRepoApiService:  GitHubRepoApiService): GitHubRepository {
        return GitHubRepository(application,gitHubRepoApiService)
    }
}