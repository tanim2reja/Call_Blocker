package com.jolpai.callblocker.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BlockedNumber::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun blockedNumberDao(): BlockedNumberDao


}