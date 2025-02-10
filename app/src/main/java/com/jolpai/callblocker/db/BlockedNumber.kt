package com.jolpai.callblocker.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "BlockedNumber")
data class BlockedNumber(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val number: String)
