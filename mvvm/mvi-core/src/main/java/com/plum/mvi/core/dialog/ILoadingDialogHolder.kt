package com.plum.mvi.core.dialog

import android.content.Context

/**
 * @Author: meixianbing
 * @Date: 2023/6/19 下午8:08
 * @Description:
 */

interface ILoadingDialogHolder {

    fun showLoadingDialog(
        context: Context,
        message: String?
    )

    fun dismissLoadingDialog()
}

class UiLoadingDialogHolder : ILoadingDialogHolder {

    private var loadingDialog: LoadingDialog? = null

    override fun showLoadingDialog(
        context: Context,
        message: String?
    ) {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog(context)
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
        loadingDialog = null
    }
}
