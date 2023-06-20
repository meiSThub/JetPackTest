package com.plum.mvi.core.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.plum.mvi.core.kt.LiveEvents
import com.plum.mvi.core.kt.asLiveData

/**
 * @Author: meixianbing
 * @Date: 2023/6/19 下午4:14
 * @Description: mvi ViewModel
 *
 * 【STATE】：表示需要记忆的页面状态，如数据绑定等，保留LiveData的数据倒罐特性
 * 【EVENT】：表示一次性事件，只会回调一次
 * 【ACTION】：表示UI层对ViewModel层发送的动作事件，即ui层的操作动作
 */
abstract class MviViewModel<STATE, EVENT, ACTION> : BaseViewModel(), IUiActionHandler<ACTION> {
    // ui状态，在生命周期变化的时候，可以多次回调
    protected val _viewState = MutableLiveData(initState())

    abstract fun initState(): STATE

    val viewState: LiveData<STATE> = _viewState

    // ui事件，在生命周期变化的时候，不会多次回调，回调一次之后，事件就会被清除掉，如：点击事件
    protected val _viewEvent = LiveEvents<EVENT>()
    val viewEvent = _viewEvent.asLiveData()


}