package com.github.demo.data.datamanager

import com.github.demo.data.preferences.PreferenceManager
import com.github.demo.data.remote.ApiClient
import com.github.demo.data.remote.ApiService
import com.github.demo.data.roomdatabase.AppDatabase

class DataManager : IDataManager {

    companion object {

        private var SINGLETON_INSTANCE: IDataManager? = null
        const val BASE_URL = "https://api.github.com/"
        const val SESSION_EXPIRE: Int = 401
        const val NO_INTERNET: Int = 502
        const val INVALID_REQUEST: Int = 422
        const val NOT_FOUND: Int = 404
        const val TRY_AGAIN: Int = 400


        fun getInstance(): IDataManager {
            if (SINGLETON_INSTANCE == null) {
                SINGLETON_INSTANCE = DataManager()
            }
            return SINGLETON_INSTANCE!!
        }
    }

    override fun getPreference() = PreferenceManager.getInstance()

    override fun getDatabase(): AppDatabase {
        return AppDatabase.getDatabase()
    }

    override fun getRemote(): ApiService {
        return ApiClient.getInstance(BASE_URL).create(ApiService::class.java)
    }
}