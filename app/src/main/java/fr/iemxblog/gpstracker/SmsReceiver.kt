package fr.iemxblog.gpstracker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Telephony
import android.support.v4.content.LocalBroadcastManager
import android.telephony.SmsMessage
import android.util.Log
import android.widget.Toast

class SmsReceiver : BroadcastReceiver() {

    private val TAG = "SMSBroadcastReceiver"

    override fun onReceive(context: Context, intent: Intent) {

        Log.i(TAG, "Intent received: ${intent.action}")

        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            val bundle = intent.extras
            val info = intent.getStringExtra("format")

            if (bundle != null) {
                val pdus = bundle.get("pdus") as Array<Any>
                val messages = arrayOfNulls<SmsMessage>(pdus.size)
                for (i in pdus.indices)
                    messages[i] = SmsMessage.createFromPdu(pdus[i] as ByteArray, info)
                if (messages.size > -1) {
                    Log.i(TAG, "Message received: " + messages[0]?.getMessageBody())
                    Toast.makeText(context, messages[0]?.getMessageBody(), Toast.LENGTH_LONG).show()
                    checkSMS(messages[0]?.getMessageBody(), context)
                }

            }
        }
    }

    private fun checkSMS(SMS: String?, context: Context) {
        if (SMS == "STOP") {
            LocalBroadcastManager.getInstance(context).unregisterReceiver(this)
        }
    }
}
