package com.plum.mvi.core.activity

import androidx.viewbinding.ViewBinding
import com.plum.mvi.core.kt.observeEvent
import com.plum.mvi.core.vm.MviViewModel

/**
 * @Author: meixianbing
 * @Date: 2023/6/19 下午5:31
 * @Description: mvi Activity基类
 */
abstract class MviActivity<VB : ViewBinding, STATE, EVENT, ACTION, VM : MviViewModel<STATE, EVENT, ACTION>> :
    MvvmActivity<VB, VM>() {

    override fun registerObserver() {
        super.registerObserver()
        viewModel.viewState.observe(this) { state: STATE ->
            doOnStateChanged(state)
        }
        viewModel.viewEvent.observeEvent(this) { event: EVENT ->
            doOnEventChanged(event)
        }
    }

    /**
     * 处理ui状态
     */
    open fun doOnStateChanged(state: STATE) {

    }

    /**
     * 处理ui事件
     */
    open fun doOnEventChanged(event: EVENT) {

    }
}