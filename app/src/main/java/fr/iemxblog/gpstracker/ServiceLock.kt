package fr.iemxblog.gpstracker

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.provider.Telephony
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import android.widget.Toast

class ServiceLock : Service() {

    var myReceiver: SmsReceiver? = null
    var msgCom: BroadcastReceiver? = null

    override fun onCreate() {
        initSMS()
        initBroadcast()
        Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show()
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int = START_STICKY

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(this.applicationContext).unregisterReceiver(msgCom)
        unregisterReceiver(myReceiver)
        Log.d("ServiceLock", "service done")
        super.onDestroy()
    }

    override fun onBind(p0: Intent?): IBinder? = null

    private fun initSMS() {
        myReceiver = SmsReceiver()
        this.registerReceiver(myReceiver, android.content.IntentFilter("android.provider.Telephony.SMS_RECEIVED"))
    }

    private fun initBroadcast() {
        msgCom = MsgCom()
        LocalBroadcastManager.getInstance(this).registerReceiver(msgCom, IntentFilter("lockMyPhone"))
    }

    inner class MsgCom: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == "lockMyPhone") {
                val message = intent.getStringExtra("message")
                Log.d("receiver", "Got message: " + message)
                if(message == "stopService") {
                    this@ServiceLock.stopSelf()
                    Log.d("receiver", "Service stopped")
                }
            }
        }
    }
}
