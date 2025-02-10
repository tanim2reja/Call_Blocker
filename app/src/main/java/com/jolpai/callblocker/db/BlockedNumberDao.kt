package com.jolpai.callblocker.db


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface BlockedNumberDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBlockedNumber(blockedNumber: BlockedNumber)

    @Query("SELECT * FROM BlockedNumber")
    fun getBlockedNumbers(): Flow<List<BlockedNumber>>



}