package com.plum.pagingtest

import android.app.Application
import com.elvishew.xlog.LogConfiguration
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout

/**
 * @Author: meixianbing
 * @Date: 2023/4/27 下午2:59
 * @Description:
 */
class MyApplication : Application() {
    companion object {
        init {
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
                ClassicsHeader(context)
            }
            SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
                ClassicsFooter(context)
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        XLog.init(
            LogConfiguration.Builder().logLevel(LogLevel.ALL).tag("PagingTest")
                .enableThreadInfo()                                    // 允许打印线程信息，默认禁止
                .enableStackTrace(2)                                   // 允许打印深度为 2 的调用栈信息，默认禁止
                .enableBorder()                                        // 允许打印日志边框，默认禁止
                .build()
        )
    }
}