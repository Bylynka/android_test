package com.agileengine.test.api.repos

import com.agileengine.test.models.Repo
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET

interface IReposApi {

    @GET("repos")
    fun fetchRepos(): Call<List<Repo>>
}