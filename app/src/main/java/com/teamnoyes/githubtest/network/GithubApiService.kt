package com.teamnoyes.githubtest.network

import com.teamnoyes.githubtest.BuildConfig
import com.teamnoyes.githubtest.data.api.GithubService
import com.teamnoyes.githubtest.utils.ConstValues.GITHUB_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GithubApiService {
    val retrofit: GithubService = Retrofit.Builder()
        .baseUrl(GITHUB_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(buildOkHttpClient())
        .build()
        .create(GithubService::class.java)

    private fun buildOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("Accept", "application/vnd.github.v3+json")
                    .addHeader("Authorization", "token ${BuildConfig.GITHUB_API_KEY}")
                    .build()
                return@Interceptor chain.proceed(request)
            })
            .addNetworkInterceptor(
                HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    }else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                }
            )
            .build()
}