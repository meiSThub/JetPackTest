package com.plum.mvi.core.activity

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.plum.mvi.core.StatusViewEvent
import com.plum.mvi.core.kt.observeEvent
import com.plum.mvi.core.vm.BaseViewModel

/**
 * @Author: meixianbing
 * @Date: 2023/6/19 下午5:20
 * @Description:MVVM 基类
 */
abstract class MvvmActivity<VB : ViewBinding, VM : BaseViewModel> : LoadingActivity<VB>() {

    abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerObserver()
    }

    open fun registerObserver() {
        viewModel.getStatusEvent().observeEvent(this) { event: StatusViewEvent ->
            when (event) {
                is StatusViewEvent.Loading -> showLoadingView(event.message)
                StatusViewEvent.Content -> showContentView()
                StatusViewEvent.Empty -> showEmptyView()
                is StatusViewEvent.Error -> showErrorView(event.message)
            }
        }
    }
}