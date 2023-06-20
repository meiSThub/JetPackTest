package com.plum.mvi.core.vm

import androidx.lifecycle.ViewModel

/**
 * @Author: meixianbing
 * @Date: 2023/6/19 下午2:45
 * @Description:
 */
abstract class BaseViewModel : ViewModel(), IStatusViewVMHandler by StatusViewViewModel(),
    ILoadingDialogVMHandler by LoadingDialogViewModel() {

}