package com.plum.roomtest.dao

import androidx.room.*
import com.plum.roomtest.data.WordEntity

/**
 * @Author: meixianbing
 * @Date: 2023/5/29 上午10:50
 * @Description:
 */

@Dao
interface WordDao {
    /**
     * 插入多个数据
     * onConflict：冲突策略
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(words: List<WordEntity>)

    /**
     * 插入单个数据
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: WordEntity)

    // 获取所有数据
    @Query("select * from word_table")
    suspend fun queryAll(): MutableList<WordEntity>

    // 根据id获取一个数据
    @Query("select * from word_table where id=:id")
    suspend fun queryById(id: Long): WordEntity?

    // 删除表中所有数据
    @Query("delete from word_table")
    suspend fun deleteAll()

    // 通过id修改数据
    @Query("update word_table set word=:word where id=:id")
    suspend fun updateData(id: Long, word: String)

    // 通过id删除数据
    @Query("delete from word_table where id=:id")
    suspend fun deleteById(id: Long)

    // 根据属性值删除数据
    @Query("delete from word_table where word=:word")
    suspend fun deleteByName(word: String)
}