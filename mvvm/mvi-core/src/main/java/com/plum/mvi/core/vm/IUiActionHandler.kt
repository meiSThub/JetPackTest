package com.plum.mvi.core.vm

/**
 * @Author: meixianbing
 * @Date: 2023/6/19 下午4:17
 * @Description:ViewModel处理UI层的事件
 */
interface IUiActionHandler<Action> {
    /**
     * ViewModel处理UI层的事件
     */
    fun handleAction(action: Action)
}