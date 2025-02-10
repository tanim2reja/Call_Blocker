package com.jolpai.callblocker.service

import android.telecom.Connection
import android.telecom.ConnectionRequest
import android.telecom.ConnectionService
import android.telecom.PhoneAccountHandle
import android.util.Log

class MyConnectionService : ConnectionService() {

    override fun onCreate() {
        super.onCreate()
        Log.e("TAG", "ConnectionService created")
    }

    override fun onCreateOutgoingConnection(
        connectionManagerPhoneAccount: PhoneAccountHandle?,
        request: ConnectionRequest?
    ): Connection {
        Log.e("TAG", "ConnectionService created")
        return super.onCreateOutgoingConnection(connectionManagerPhoneAccount, request)
    }
}