package com.jolpai.callblocker.db

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BlockedNumberRepository @Inject constructor (private val dao: BlockedNumberDao){

    fun getBlockedNumbers(): Flow<List<BlockedNumber>> = dao.getBlockedNumbers()

    suspend fun insertBlockedNumber(number: String) {
        dao.insertBlockedNumber(BlockedNumber(number = number))
    }
}