package com.jolpai.callblocker

import android.app.Application
import androidx.room.Room
import com.jolpai.callblocker.db.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application(){
    lateinit var database: AppDatabase
    override fun onCreate() {
        super.onCreate()
        // Initialize Room database
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "app_call_blocker_database"
        ).build()
    }
}