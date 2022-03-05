package com.github.demo.base

import android.app.Activity
import android.app.Application
import android.content.pm.ActivityInfo
import android.os.Bundle


class BaseApplication : Application() {
    companion object {
        @get:Synchronized
        lateinit var SINGLETON_INSTANCE: BaseApplication
    }

    override fun onCreate() {
        super.onCreate()
        SINGLETON_INSTANCE = this
    }
}