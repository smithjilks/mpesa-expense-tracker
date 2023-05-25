package com.smithjilks.mpesaexpensetracker.core.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log
import com.smithjilks.mpesaexpensetracker.core.constants.AppConstants
import com.smithjilks.mpesaexpensetracker.core.repository.AppDatabaseRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class SmsBroadcastReceiver : BroadcastReceiver() {
    @Inject lateinit var repository: AppDatabaseRepository
    @Inject lateinit var ioDispatcher: CoroutineDispatcher
    companion object {
        private val TAG = SmsBroadcastReceiver::class.java.name

    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (!intent?.action.equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) return
        val extractMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        extractMessages.forEach { smsMessage ->
            if (smsMessage.displayOriginatingAddress == AppConstants.MPESA) {
                // handle smsMessage.displayMessageBody
                val record = CoreUtils.createRecordFromMpesaMessage(smsMessage.displayMessageBody)
                record?.let {
                    CoroutineScope(ioDispatcher).launch {
                        repository.insertRecord(it)
                        Log.d(TAG, "Added $record")
                        Log.d(TAG, smsMessage.displayOriginatingAddress)
                    }
                }
            }
        }
    }


}
