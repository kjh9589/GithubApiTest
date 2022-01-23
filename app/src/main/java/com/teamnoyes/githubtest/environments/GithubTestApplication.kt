package com.teamnoyes.githubtest.environments

import android.app.Application
import com.teamnoyes.githubtest.data.datasource.impl.GithubDataSourceImpl
import com.teamnoyes.githubtest.data.repository.GithubRepositoryImpl
import com.teamnoyes.githubtest.network.GithubApiService
import com.teamnoyes.githubtest.utils.NetworkLiveData

class GithubTestApplication: Application() {
    lateinit var githubRepository: GithubRepositoryImpl
    override fun onCreate() {
        super.onCreate()
        githubRepository = GithubRepositoryImpl(GithubDataSourceImpl(GithubApiService.retrofit))
        NetworkLiveData.init(this)
    }
}