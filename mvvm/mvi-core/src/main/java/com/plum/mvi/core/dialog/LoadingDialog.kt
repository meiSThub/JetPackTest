package com.plum.mvi.core.dialog

import android.app.Dialog
import android.content.Context
import com.plum.mvi.core.R
import com.plum.mvi.core.databinding.DialogProgressLayoutBinding

/**
 * Time:2023/1/10
 * Author:bin.yan
 * Description:只有content，确定按钮，和取消按钮
 */
class LoadingDialog(context: Context, themeResId: Int = R.style.commonDialog) :
    Dialog(context, themeResId) {

    val binding: DialogProgressLayoutBinding by lazy {
        DialogProgressLayoutBinding.inflate(layoutInflater)
    }

    init {
        setContentView(binding.root)
        window?.attributes?.width =
            context.resources.getDimension(R.dimen.loading_dialog_width).toInt()
        window?.attributes?.height =
            context.resources.getDimension(R.dimen.loading_dialog_height).toInt()
    }
}