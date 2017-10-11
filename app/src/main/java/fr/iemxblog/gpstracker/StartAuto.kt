package fr.iemxblog.gpstracker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class StartAuto : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.i("StartAuto", "onReceive")
        val i = Intent(context, ServiceLock::class.java)
        context.startService(i)
    }
}
