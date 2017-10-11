package fr.iemxblog.gpstracker

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast

class ServiceLock : Service() {

    override fun onCreate() {
        Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show()
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int = START_STICKY

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBind(p0: Intent?): IBinder? = null
}
