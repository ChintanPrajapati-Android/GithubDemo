package com.github.demo.base

import androidx.lifecycle.ViewModel
import com.github.demo.data.datamanager.DataManager
import com.github.demo.data.preferences.IPreference
import com.github.demo.data.remote.ApiService
import com.github.demo.data.roomdatabase.AppDatabase

open class BaseViewModel : ViewModel() {

    fun getPreference(): IPreference {
        return DataManager.getInstance().getPreference()
    }

    fun getDatabase(): AppDatabase {
        return DataManager.getInstance().getDatabase()
    }

    fun getRemote(): ApiService {
        return DataManager.getInstance().getRemote()
    }
}