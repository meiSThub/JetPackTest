package com.plum.pagingtest.http

import com.elvishew.xlog.XLog
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject

/**
 * @Author: meixianbing
 * @Date: 2023/4/27 下午4:45
 * @Description:
 */
object Logger : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        try {
            JSONObject(message)
            XLog.json(message)
        } catch (e: Exception) {
            HttpLoggingInterceptor.Logger.DEFAULT.log(message)
        }
    }
}