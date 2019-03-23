package com.smsslave

import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import okio.ByteString
import java.io.IOException
import java.lang.Exception
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity() {

    private val URL_LISTENER = "wss://192.168.1.100:8080"
    private lateinit var client : OkHttpClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startWS()
    }

    fun startWS() {
        try {
            client = OkHttpClient()
            val request = Request.Builder().url(URL_LISTENER).build()
            val listener = CustomWebSocketListener()
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
            logTextView.text = allLog
        } catch (e: IOException) {
            Log.i("LOG ERROR", e.message)
        }
    }

//    @RequiresApi(Build.VERSION_CODES.O)
//    open class WebSocketListener(activity: MainActivity) : WebSocketAdapter()
//    {
//        private val activity = activity
//
//        override fun onTextMessage(websocket: WebSocket?, text: String?) {
//            super.onTextMessage(websocket, text)
//            try {
//                val jsonRequest = JSONObject(text)
//                val smsManager = SmsManager.getDefault()
//
//                smsManager.sendTextMessage(
//                    jsonRequest.getString("phoneNumber"),
//                    null,
//                    jsonRequest.getString("message") + jsonRequest.getString("code"),
//                    null,
//                    null
//                )
//                activity.logAction(jsonRequest.getString("phoneNumber"), jsonRequest.getString("message"), jsonRequest.getString("code"))
//            } catch (e: JSONException) {
//                e.printStackTrace()
//                Log.e("ERROR", e.message)
//                Log.e("ERROr", "Received: $text")
//            }
//
//        }
//
//        override fun onConnectError(websocket: WebSocket?, exception: WebSocketException?) {
//            super.onConnectError(websocket, exception)
//            Log.e("ERROR", exception?.message)
//        }
//    }

    class CustomWebSocketListener() : okhttp3.WebSocketListener() {

        //private val activity = activity

        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosing(webSocket, code, reason)
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            super.onMessage(webSocket, bytes)
        }

    }
}
