package com.github.demo.data.datamanager

import com.github.demo.data.preferences.IPreference
import com.github.demo.data.remote.ApiService
import com.github.demo.data.roomdatabase.AppDatabase

interface IDataManager {
    fun getPreference(): IPreference
    fun getDatabase(): AppDatabase
    fun getRemote(): ApiService
}