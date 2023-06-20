package com.plum.mvi.core.vm

import androidx.lifecycle.LiveData

/**
 * @Author: meixianbing
 * @Date: 2023/6/19 下午3:14
 * @Description: loading 弹框相关操作
 */
interface ILoadingDialogHandler {
    /**
     * 显示loading弹框
     */
    fun showLoadingDialog(message: String? = null)

    /**
     * 取消显示loading弹框
     */
    fun dismissLoadingDialog()
}

/**
 * 显示Loading弹框，ViewModel层实现接口
 */
interface ILoadingDialogVMHandler : ILoadingDialogHandler {
    fun getLoadingDialog(): LiveData<List<Pair<Boolean, String>>>
}