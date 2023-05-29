package com.plum.roomtest.dao

import androidx.room.*
import com.plum.roomtest.data.OrderInfoEntity

/**
 * @Author: meixianbing
 * @Date: 2023/5/29 上午10:50
 * @Description:
 */

@Dao
interface OrderInfoDao {
    /**
     * 插入多个数据
     * onConflict：冲突策略
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(words: List<OrderInfoEntity>)

    /**
     * 插入单个数据
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: OrderInfoEntity)

    // 获取所有数据
    @Query("select * from order_info_table")
    suspend fun queryAll(): MutableList<OrderInfoEntity>

    // 根据id获取一个数据
    @Query("select * from order_info_table where id=:id")
    suspend fun queryById(id: Long): OrderInfoEntity?

    // 删除表中所有数据
    @Query("delete from order_info_table")
    suspend fun deleteAll()

    // 通过id修改数据
    @Query("update order_info_table set order_info=:order_info where id=:id")
    suspend fun updateData(id: Long, order_info: String)

    // 通过id删除数据
    @Query("delete from order_info_table where id=:id")
    suspend fun deleteById(id: Long)

    // 根据属性值删除数据
    @Query("delete from order_info_table where order_info=:order_info")
    suspend fun deleteByName(order_info: String)
}