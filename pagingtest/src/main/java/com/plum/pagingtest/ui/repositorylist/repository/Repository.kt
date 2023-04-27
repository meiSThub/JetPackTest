package com.plum.pagingtest.ui.repositorylist.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.plum.pagingtest.bean.RepositoryItem
import kotlinx.coroutines.flow.Flow

/**
 * @Author: meixianbing
 * @Date: 2023/4/27 下午1:36
 * @Description:
 */
class Repository {

    companion object {
        const val PAGE_SIZE = 10
    }

    /**
     * 获取仓库列表
     */
    fun getRepositoryList(): Flow<PagingData<RepositoryItem>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE, initialLoadSize = PAGE_SIZE),
            pagingSourceFactory = { GithubPagingSource() }
        ).flow
    }
}