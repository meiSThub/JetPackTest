package com.plum.mvi.core.vm

import androidx.lifecycle.LiveData
import com.plum.mvi.core.kt.LiveEvents
import com.plum.mvi.core.kt.asLiveData
import com.plum.mvi.core.kt.setEvent

/**
 * @Author: meixianbing
 * @Date: 2023/6/19 下午4:08
 * @Description: ViewModel 显示loading弹框
 */
class LoadingDialogViewModel : ILoadingDialogVMHandler {
    private val _loadingDialog = LiveEvents<Pair<Boolean, String>>()
    private val loadingDialog = _loadingDialog.asLiveData()

    override fun getLoadingDialog(): LiveData<List<Pair<Boolean, String>>> {
        return loadingDialog
    }

    override fun showLoadingDialog(message: String?) {
        _loadingDialog.setEvent(Pair(true, message ?: ""))
    }

    override fun dismissLoadingDialog() {
        _loadingDialog.setEvent(Pair(false, ""))
    }
}