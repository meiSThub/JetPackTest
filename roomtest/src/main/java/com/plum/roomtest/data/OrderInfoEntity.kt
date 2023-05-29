package com.plum.roomtest.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Author: meixianbing
 * @Date: 2023/5/29 下午3:24
 * @Description:
 */

@Entity(tableName = "order_info_table")
data class OrderInfoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "order_id")
    val orderId: String? = null,
    @ColumnInfo(name = "order_info")
    val orderInfo: String? = null,
)