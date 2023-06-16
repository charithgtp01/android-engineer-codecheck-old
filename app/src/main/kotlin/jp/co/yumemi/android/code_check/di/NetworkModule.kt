package jp.co.yumemi.android.code_check.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    @Singleton
    @Provides
    fun provideBaseUrl(@ApplicationContext context: Context): String {
        return BASE_URL
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create();
    }

    @Singleton
    @Provides
    fun provideHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val okHttpClient =
            OkHttpClient.Builder().addInterceptor(ServiceInterceptor())
        return okHttpClient.build()
    }

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

    @Singleton
    @Provides
    fun provideGithubAccountApiService(retrofit: Retrofit): GitHubRepoApiService {
        return retrofit.create(GitHubRepoApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideDogRepository(gitHubRepoApiService:  GitHubRepoApiService): GitHubRepository {
        return GitHubRepository(gitHubRepoApiService)
    }
}