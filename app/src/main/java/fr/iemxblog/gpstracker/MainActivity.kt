package fr.iemxblog.gpstracker

import android.Manifest
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Button
import android.widget.EditText
import fr.iemxblog.gpstracker.Sms.sendSMS

enum class MyPermissions {
    MY_PERMISSIONS_SEND_SMS,
    MY_PERMISSIONS_RECEIVE_SMS
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextPhone = findViewById(R.id.editTextPhone) as EditText
        val editTextPass = findViewById(R.id.editTextPass) as EditText
        val buttonUnlock = findViewById(R.id.buttonUnlock) as Button
        val buttonStart = findViewById(R.id.buttonStart) as Button
        val buttonStop = findViewById(R.id.buttonStop) as Button

        buttonUnlock.setOnClickListener {

        }

        buttonStart.setOnClickListener {

        }

        buttonStop.setOnClickListener {

        }

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            Log.i("MyPermissions", "Requesting SEND_SMS permission")
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS), MyPermissions.MY_PERMISSIONS_SEND_SMS.ordinal)
        }

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            Log.i("MyPermissions", "Requesting RECEIVE_SMS permission")
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECEIVE_SMS), MyPermissions.MY_PERMISSIONS_RECEIVE_SMS.ordinal)
        }

        val myReceiver = SmsReceiver()
        this.registerReceiver(myReceiver, IntentFilter("android.provider.Telephony.SMS_RECEIVED"))

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            MyPermissions.MY_PERMISSIONS_SEND_SMS.ordinal -> Log.i("MyPermissions", "Received SEND_SMS permission")
            MyPermissions.MY_PERMISSIONS_RECEIVE_SMS.ordinal -> Log.i("MyPermissions", "Received RECEIVE_SMS permission")
        }
    }
}
