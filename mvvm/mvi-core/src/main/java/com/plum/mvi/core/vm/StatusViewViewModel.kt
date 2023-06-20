package com.plum.mvi.core.vm

import androidx.lifecycle.LiveData
import com.plum.mvi.core.StatusViewEvent
import com.plum.mvi.core.kt.LiveEvents
import com.plum.mvi.core.kt.asLiveData
import com.plum.mvi.core.kt.setEvent

/**
 * @Author: meixianbing
 * @Date: 2023/6/19 下午2:55
 * @Description:状态View处理类
 */
class StatusViewViewModel : IStatusViewHandler, IStatusViewVMHandler {
    private val _statusEvent = LiveEvents<StatusViewEvent>()
    private val statusEvent = _statusEvent.asLiveData()

    override fun showLoadingView(message: String?) {
        _statusEvent.setEvent(StatusViewEvent.Loading(message))
    }

    override fun showContentView() {
        _statusEvent.setEvent(StatusViewEvent.Content)
    }

    override fun showEmptyView() {
        _statusEvent.setEvent(StatusViewEvent.Empty)
    }

    override fun showErrorView(message: String?) {
        _statusEvent.setEvent(StatusViewEvent.Error(message))
    }

    override fun getStatusEvent(): LiveData<List<StatusViewEvent>> {
        return statusEvent
    }
}