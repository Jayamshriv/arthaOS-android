package com.jayam.artha_os.feature.sms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SmsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        Log.e("message_message", intent?.action.toString())

        if (intent?.action != Telephony.Sms.Intents.SMS_RECEIVED_ACTION)
            return

        val pendingResult = goAsync()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val entryPoint = EntryPointAccessors.fromApplication(
                    context.applicationContext,
                    SmsReceiverEntryPoint::class.java
                )
                val processor = entryPoint.smsProcessor()

                Telephony.Sms.Intents.getMessagesFromIntent(intent).forEach {
                    Log.e("message_message", it.messageBody .toString())
                    processor.process(
                        sender = it.displayOriginatingAddress.orEmpty(),
                        body = it.messageBody.orEmpty(),
                        timestamp = it.timestampMillis
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                pendingResult.finish()
            }
        }
    }
}