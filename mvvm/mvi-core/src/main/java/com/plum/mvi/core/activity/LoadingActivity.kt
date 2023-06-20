package com.plum.mvi.core.activity

import androidx.viewbinding.ViewBinding
import com.plum.mvi.core.dialog.LoadingDialog
import com.plum.mvi.core.vm.ILoadingDialogHandler
import com.plum.mvi.core.vm.IStatusViewHandler

/**
 * @Author: meixianbing
 * @Date: 2023/6/19 下午7:09
 * @Description:
 */
abstract class LoadingActivity<VB : ViewBinding> : StatusViewActivity<VB>(),
    IStatusViewHandler, ILoadingDialogHandler {

    private var loadingDialog: LoadingDialog? = null

    override fun showLoadingView(message: String?) {
        statusView?.showLoading()
    }

    override fun showContentView() {
        statusView?.showContent()
    }

    override fun showEmptyView() {
        statusView?.showEmpty()
    }

    override fun showErrorView(message: String?) {
        statusView?.showError(message)
    }

    override fun showLoadingDialog(message: String?) {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog(this)
        }
        loadingDialog!!.let { dialog ->
            if (!dialog.isShowing) {
                dialog.show()
            }
        }
    }

    override fun dismissLoadingDialog() {
        loadingDialog?.let { dialog ->
            if (dialog.isShowing) {
                dialog.dismiss()
            }
        }
    }
}