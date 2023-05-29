package com.plum.roomtest.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.plum.roomtest.dao.WordDao
import com.plum.roomtest.data.WordEntity

/**
 * @Author: meixianbing
 * @Date: 2023/5/29 上午11:18
 * @Description:
 *
 * 创建数据库的类是RoomDatabase，先创建一个类继承它，并@Database来标注这个自定义类，@Database注解里面还有几个参数需要理解一下：
 * entities：指定添加进来的数据库表，这里数组形式添加，如果项目用到多个表可以用逗号隔开添加进来；
 * version：当前数据库的版本号，当需要版本升级会用到；
 * exportSchema：表示导出为文件模式，默认为true,这里要设置为false，不然会报警告。
 */
@Database(entities = [WordEntity::class], version = 1, exportSchema = false)
abstract class WordDB : RoomDatabase() {
    companion object {
        @Volatile
        private var instance: WordDB? = null
        private const val DB_NAME = "jetpack_room.db"

        fun getInstance(context: Context): WordDB {
            if (instance == null) {
                synchronized(WordDB::class) {
                    if (instance == null) {
                        instance = createInstance(context)
                    }
                }
            }
            return instance!!
        }

        private fun createInstance(context: Context): WordDB {
            return Room.databaseBuilder(context.applicationContext, WordDB::class.java, DB_NAME)
                .allowMainThreadQueries()
                .build()
        }
    }

    abstract fun getWordDao(): WordDao
}