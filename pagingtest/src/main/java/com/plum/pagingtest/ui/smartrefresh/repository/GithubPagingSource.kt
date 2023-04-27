package com.plum.pagingtest.ui.smartrefresh.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.elvishew.xlog.XLog
import com.plum.pagingtest.bean.RepositoryItem
import com.plum.pagingtest.http.retrofit.GithubApiManager

/**
 * @Author: meixianbing
 * @Date: 2023/4/27 上午11:43
 * @Description: 分页加载数据源
 */
class GithubPagingSource : PagingSource<Int, RepositoryItem>() {

    override fun getRefreshKey(state: PagingState<Int, RepositoryItem>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepositoryItem> {
        return try {
            val page = params.key ?: 1
            val pageSize = params.loadSize
            XLog.i("load: page=$page,pageSize=$pageSize")
            val repositoryResData =
                GithubApiManager.githubServiceApi.getRepositories(page, pageSize)
            val items = repositoryResData.items
            val preKey = if (page > 1) page - 1 else null
            val nextKey = if (items.isNotEmpty() && items.size >= pageSize) page + 1 else null
            LoadResult.Page(items, preKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}