package com.smithjilks.mpesaexpensetracker.core.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log
import com.smithjilks.mpesaexpensetracker.core.constants.AppConstants


class SmsBroadcastReceiver(private val callback: (mpesaMessage: String) -> Unit) :
    BroadcastReceiver() {
    companion object {
        private val TAG = SmsBroadcastReceiver::class.java.name

    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (!intent?.action.equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) return
        val extractMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        extractMessages.forEach { smsMessage ->
            if (smsMessage.displayOriginatingAddress == AppConstants.MPESA) {
                callback.invoke(smsMessage.displayMessageBody)
                Log.d(TAG, smsMessage.displayMessageBody)
                Log.d(TAG, smsMessage.displayOriginatingAddress)
            }
        }
    }

}
