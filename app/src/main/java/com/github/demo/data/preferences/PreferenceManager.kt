package com.github.demo.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.github.demo.base.BaseApplication

class PreferenceManager : IPreference {

    companion object {
        private var SINGLETON_INSTANCE: IPreference? = null

        fun getInstance(): IPreference {
            if (SINGLETON_INSTANCE == null) {
                SINGLETON_INSTANCE = PreferenceManager()
            }
            return SINGLETON_INSTANCE!!
        }

        const val USER_NAME = "userName"
        const val PASSWORD = "password"
    }

    private val sharedPreferencesEditor: SharedPreferences.Editor
    private val sharedPreferences: SharedPreferences

    init {
        val sharedPreferencesName = "spDemo"
        sharedPreferences = BaseApplication.SINGLETON_INSTANCE.getSharedPreferences(
            sharedPreferencesName,
            Context.MODE_PRIVATE
        )
        sharedPreferencesEditor = sharedPreferences.edit()
        sharedPreferencesEditor.apply()
    }


    override fun putString(key: String, value: String) {
        sharedPreferencesEditor.putString(key, value).apply()
    }

    override fun getString(key: String): String {
        sharedPreferences.getString(key, "")?.let {
            return it
        }
        return ""
    }

    override fun getString(key: String, defaultValue: String?): String {
        sharedPreferences.getString(key, defaultValue)?.let {
            return it
        }
        return ""
    }

    override fun putInt(key: String, value: Int) {
        sharedPreferencesEditor.putInt(key, value).apply()
    }

    override fun getInt(key: String): Int {
        return sharedPreferences.getInt(key, 0)
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    override fun putBoolean(key: String, value: Boolean) {
        sharedPreferencesEditor.putBoolean(key, value).apply()
    }

    override fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    override fun putLong(key: String, value: Long) {
        sharedPreferencesEditor.putLong(key, value).apply()
    }

    override fun getLong(key: String): Long {
        return sharedPreferences.getLong(key, 0)
    }

    override fun putFloat(key: String, value: Float) {
        sharedPreferencesEditor.putFloat(key, value).apply()
    }

    override fun getFloat(key: String): Float {
        return sharedPreferences.getFloat(key, 0f)
    }
}