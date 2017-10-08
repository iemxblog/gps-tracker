package fr.iemxblog.gpstracker.Sms

import android.Manifest
import android.app.Activity
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.telephony.SmsManager
import android.widget.Toast

/**
 * Created by maxime on 06/10/17.
 */

fun sendSMS(phoneNumber: String, message: String, activity: Activity) {
    val SENT = "SMS_SENT"
    val DELIVERED = "SMS_DELIVERED"

    val myContext: Context = activity.applicationContext

    val sentPI: PendingIntent = PendingIntent.getBroadcast(myContext, 0, Intent(SENT), 0)
    val deliveredPI: PendingIntent = PendingIntent.getBroadcast(myContext, 0, Intent(DELIVERED), 0)

    myContext.registerReceiver(object: BroadcastReceiver() {
        override fun onReceive(arg0: Context, arg1: Intent) {
            when(resultCode) {
                Activity.RESULT_OK -> Toast.makeText(myContext.applicationContext, "SMS Sent", Toast.LENGTH_SHORT).show()
                SmsManager.RESULT_ERROR_GENERIC_FAILURE -> Toast.makeText(myContext.applicationContext, "Generic failure", Toast.LENGTH_SHORT).show()
                SmsManager.RESULT_ERROR_NO_SERVICE -> Toast.makeText(myContext.applicationContext, "No service", Toast.LENGTH_SHORT).show()
                SmsManager.RESULT_ERROR_NULL_PDU -> Toast.makeText(myContext.applicationContext, "Null PDU", Toast.LENGTH_SHORT).show()
                SmsManager.RESULT_ERROR_RADIO_OFF -> Toast.makeText(myContext.applicationContext, "Radio off", Toast.LENGTH_SHORT).show()
            }
        }
    }, IntentFilter(SENT))

    myContext.registerReceiver(object: BroadcastReceiver() {
        override fun onReceive(arg0: Context?, arg1: Intent?) {
            when(resultCode) {
                Activity.RESULT_OK -> Toast.makeText(myContext.applicationContext, "SMS delivered", Toast.LENGTH_SHORT).show()
                Activity.RESULT_CANCELED -> Toast.makeText(myContext.applicationContext, "SMS not delivered", Toast.LENGTH_SHORT).show()
            }
        }
    }, IntentFilter(DELIVERED))

    val sms = SmsManager.getDefault()
    sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI)
}


