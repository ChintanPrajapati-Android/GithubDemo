package com.github.demo.data.roomdatabase.userdata

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_data")
data class UserDataItem(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "user_id")
        var userId: Int = 0,
        @ColumnInfo(name = "pan")
        var pan: String = "",
        @ColumnInfo(name = "date")
        var date: String = ""
)