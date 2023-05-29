package com.plum.roomtest.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Author: meixianbing
 * @Date: 2023/5/29 上午10:45
 * @Description:
 */

@Entity(tableName = "word_table")
data class WordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,// 主键
    @ColumnInfo(name = "word")
    val word: String = "",
)