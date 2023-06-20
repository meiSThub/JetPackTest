package com.plum.mvi.core

/**
 * @Author: meixianbing
 * @Date: 2023/6/19 下午2:56
 * @Description:
 */

/**
 * uiState
 */
sealed class StatusViewEvent {
    class Loading(val message: String? = null) : StatusViewEvent()
    object Content : StatusViewEvent()
    object Empty : StatusViewEvent()
    class Error(val message: String? = null) : StatusViewEvent()
}