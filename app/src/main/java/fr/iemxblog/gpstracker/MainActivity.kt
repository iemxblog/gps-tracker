package fr.iemxblog.gpstracker

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import fr.iemxblog.gpstracker.Sms.sendSMS

enum class MyPermissions {
    MY_PERMISSIONS_SEND_SMS
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myContext: Context = this.applicationContext
        val editTextPhone: EditText = findViewById(R.id.editTextPhone) as EditText
        val buttonTest: Button = findViewById(R.id.buttonTest) as Button

        buttonTest.setOnClickListener(View.OnClickListener {
            sendSMS(editTextPhone.text.toString(), "test SMS", this)
        })

        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
        if(permissionCheck != PackageManager.PERMISSION_GRANTED) {
            buttonTest.isEnabled = false
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS), MyPermissions.MY_PERMISSIONS_SEND_SMS.ordinal)
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        val buttonTest: Button = findViewById(R.id.buttonTest) as Button
        when(requestCode) {
            MyPermissions.MY_PERMISSIONS_SEND_SMS.ordinal -> buttonTest.isEnabled = true
        }
    }
}
