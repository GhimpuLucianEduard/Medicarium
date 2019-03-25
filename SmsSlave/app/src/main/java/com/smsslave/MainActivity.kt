package com.smsslave

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity() {

    private val URL_LISTENER = "ws://medicarium-2fa.herokuapp.com"
    private lateinit var client : OkHttpClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestSmsPermission()
        startWS()
    }

    fun startWS() {
        try {
            client = OkHttpClient()
            val request = Request.Builder().url(URL_LISTENER).build()
            val listener = object: WebSocketListener() {
                override fun onMessage(webSocket: WebSocket, text: String) {
                    super.onMessage(webSocket, text)
                    sendSMS(text)
                }

                override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                    super.onFailure(webSocket, t, response)
                    startWS()
                }

                override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                    super.onClosed(webSocket, code, reason)
                    Log.e("WS CLOSE", "WS Close, reason: $reason")
                }
            }
            val ws = client.newWebSocket(request, listener)
            client.dispatcher().executorService().shutdown()
        } catch (e: Exception) {
            Log.e("LOG ERROR", e.message)
        }
    }

    fun logAction(receiver: String, text: String, code: String) {
        try {
            val logString = "Receiver: $receiver, Text: $text, code: $code, ts: ${LocalDateTime.now()} \n"
            Log.i("SMS SENT", logString)
            val pref = PreferenceManager.getDefaultSharedPreferences(this)
            var allLog = pref.getString("LOG", "")
            allLog += logString
            val editor = pref.edit()
            editor.putString("LOG", allLog)
            editor.apply()
            runOnUiThread {
                logTextView.text = allLog
            }
        } catch (e: IOException) {
            Log.i("LOG ERROR", e.message)
        }
    }

    fun sendSMS(json: String) {
        try {
            val jsonRequest = JSONObject(json)
            val smsManager = SmsManager.getDefault()

            smsManager.sendTextMessage(
                jsonRequest.getString("number"),
                null,
                jsonRequest.getString("text") + jsonRequest.getString("code"),
                null,
                null
            )
            logAction(jsonRequest.getString("number"), jsonRequest.getString("text"), jsonRequest.getString("code"))
        } catch (e: JSONException) {
            e.printStackTrace()
            Log.e("ERROR", e.message)
            Log.e("ERROr", "Received: $json")
        }
    }

    private fun requestSmsPermission() {
        val permission = Manifest.permission.SEND_SMS
        val grant = ContextCompat.checkSelfPermission(this, permission)
        if (grant != PackageManager.PERMISSION_GRANTED) {
            val permission_list = arrayOfNulls<String>(1)
            permission_list[0] = permission
            ActivityCompat.requestPermissions(this, permission_list, 1)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this@MainActivity, "permission granted", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this@MainActivity, "permission not granted", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
