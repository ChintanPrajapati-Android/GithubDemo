package com.github.demo.data.roomdatabase.userdata

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDataDao {

    @Query("SELECT * FROM user_data")
    suspend fun getAllSavedUserDetail(): List<UserDataItem>

    @Insert()
    suspend fun insertUserDetail(userDataItem: UserDataItem)
}