package com.plum.roomtest.db.update

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * @Author: meixianbing
 * @Date: 2023/5/29 下午3:01
 * @Description:
 */

/**
 * jetpack_room.db 数据库升级
 */
object WordDbMigration {
    /**
     * 参数：1->2,从版本1升级到版本2
     */
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("alter table word_table add column content text")
        }
    }

    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // 新增一个表
            database.execSQL("CREATE TABLE IF NOT EXISTS order_info_table(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, order_id TEXT, order_info TEXT)")
        }
    }
}

