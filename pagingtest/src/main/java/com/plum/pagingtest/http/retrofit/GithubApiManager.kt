package com.plum.pagingtest.http.retrofit

import com.plum.pagingtest.http.GithubService
import com.plum.pagingtest.http.Logger
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @Author: meixianbing
 * @Date: 2023/4/27 上午11:37
 * @Description:
 */
object GithubApiManager {

    val githubServiceApi: GithubService by lazy {
        val retrofit = retrofit2.Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor(Logger).setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
            )
            .baseUrl(GithubService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(GithubService::class.java)
    }
}