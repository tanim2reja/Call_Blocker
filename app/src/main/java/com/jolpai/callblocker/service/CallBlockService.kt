package com.jolpai.callblocker.service

import android.telecom.Call
import android.telecom.CallScreeningService
import android.util.Log

class CallBlockService: CallScreeningService() {

    override fun onCreate() {
        super.onCreate()
        Log.e("TAG", "CallScreeningService created")
    }

    override fun onScreenCall(details: Call.Details) {
        val blockedNumber = "+a8801774413017" // get number from database
        //val blockedNumber = "+8801724595314" // get number from database

        val incomingNumber = details.handle.schemeSpecificPart
        Log.e("TAG",incomingNumber )
        if(blockedNumber.contains(incomingNumber)){
            val response = CallResponse.Builder()
                .setDisallowCall(true)
                .setRejectCall(true)
                .setSkipCallLog(true)
                .setSkipNotification(true)
                .build()
            respondToCall(details, response)
        }else{
            val response = CallResponse.Builder().build()
            respondToCall(details, response)
        }
    }
}