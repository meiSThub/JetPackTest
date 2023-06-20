package com.plum.mvi.core.activity

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.plum.statusview.StatusView

/**
 * @Author: meixianbing
 * @Date: 2023/6/19 下午5:03
 * @Description：状态View
 */
abstract class StatusViewActivity<VB : ViewBinding> : ViewBindingActivity<VB>() {
    protected var statusView: StatusView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        statusView = wrap(binding.root)
    }

    private fun wrap(view: View?, attachToRoot: Boolean = true): StatusView {
        if (view == null) {
            throw RuntimeException("content view can not be null")
        }

        val parent = view.parent as? ViewGroup
        var index = 0
        if (parent != null) {
            index = parent.indexOfChild(view)
            parent.removeView(view)
        }

        val layout = StatusView(view.context)
        layout.setContentView(view)

        if (attachToRoot) {
            if (parent == null) {
                throw RuntimeException("attachToRoot,parent view can not be null")
            }
            parent.addView(layout, index, view.layoutParams)
        }
        return layout
    }
}