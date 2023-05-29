package com.plum.roomtest

import android.app.Application
import com.plum.roomtest.db.WordDB

/**
 * @Author: meixianbing
 * @Date: 2023/5/29 下午1:38
 * @Description:
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // 让数据库初始化创建
        WordDB.getInstance(this)
    }
}