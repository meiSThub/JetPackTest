package com.plum.pagingtest.http

import com.plum.pagingtest.bean.RepositoryResData
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @Author: meixianbing
 * @Date: 2023/4/27 上午11:32
 * @Description:
 */
interface GithubService {

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }

    /**
     * 获取Github的 Android 相关的git仓库
     */
    @GET("search/repositories?sort=stars&q=Android")
    suspend fun getRepositories(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int
    ): RepositoryResData

}