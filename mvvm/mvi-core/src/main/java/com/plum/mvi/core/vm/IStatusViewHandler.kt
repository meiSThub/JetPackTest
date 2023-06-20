package com.plum.mvi.core.vm

import androidx.lifecycle.LiveData
import com.plum.mvi.core.StatusViewEvent

/**
 * @Author: meixianbing
 * @Date: 2023/6/19 下午2:48
 * @Description: 状态View 相关操作
 */
interface IStatusViewHandler {
    /**
     * 显示loadingView，页面级loading
     */
    fun showLoadingView(message: String? = null)

    /**
     * 显示contentView
     */
    fun showContentView()

    /**
     * 显示数据为空页面
     */
    fun showEmptyView()

    /**
     * 显示错误页面
     */
    fun showErrorView(message: String? = null)
}

interface IStatusViewVMHandler : IStatusViewHandler {
    fun getStatusEvent(): LiveData<List<StatusViewEvent>>
}