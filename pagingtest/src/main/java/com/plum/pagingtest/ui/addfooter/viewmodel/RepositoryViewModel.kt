package com.plum.pagingtest.ui.addfooter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.elvishew.xlog.XLog
import com.plum.pagingtest.bean.RepositoryItem
import com.plum.pagingtest.ui.addfooter.repository.Repository
import kotlinx.coroutines.flow.Flow

/**
 * @Author: meixianbing
 * @Date: 2023/4/27 下午1:42
 * @Description:
 */
class RepositoryViewModel : ViewModel() {

    private val repository: Repository by lazy {
        Repository()
    }

    fun getPagingData(): Flow<PagingData<RepositoryItem>> {
        XLog.i("getPagingData: start")
        return repository.getRepositoryList().cachedIn(viewModelScope)
    }
}