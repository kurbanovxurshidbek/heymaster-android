package com.heymaster.heymaster.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.heymaster.heymaster.R
import com.heymaster.heymaster.role.master.MasterActivity
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlin.random.Random

const val CHANNEL_ID = "my_channel"

class FirebaseService: FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        lateinit var intent: Intent

        Log.d("TAG", "onMessageReceived: $message")
        val type = message.data["type"]

        when(type) {
            "parsel" -> {
                intent = Intent(this, MasterActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                intent.putExtra("type", "parcel")

            }
            "update" -> {
                intent = Intent(Intent.ACTION_VIEW).also {
                    it.data = Uri.parse("https://play.google.com/store/apps/details?id=ic0der.justwater")
                    it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                }
            }
            else -> intent = Intent(Intent.ACTION_VIEW).also {
                it.data = Uri.parse("https://play.google.com/store/apps/details?id=ic0der.justwater")
                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
        }
        val requestCode = (0..10).random()
        val pendingIntent = PendingIntent.getActivity(this, requestCode, intent,
            PendingIntent.FLAG_ONE_SHOT)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(message.data["title"])
            .setContentText(message.data["body"])
            .setSmallIcon(R.drawable.app_logo)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.img))
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setStyle(
                if (message.data["image"].isNullOrEmpty()) {
                    NotificationCompat.BigTextStyle().bigText(message.data["body"])
                        .setBigContentTitle(message.data["title"])
                } else {
                    NotificationCompat.BigPictureStyle().setSummaryText(message.data["body"])
                        .bigPicture(getBitmapfromUrl(message.data["image"]))
                }
            )
            .build()

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(manager)
        }

        val notificationId = Random.nextInt()
        manager.notify(notificationId, notification)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(manager: NotificationManager) {
        val channelName = "channelName"
        val channel = NotificationChannel(CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_HIGH).apply {
            description = "My Channel describtion"
            enableLights(true)
            lightColor = Color.BLUE
        }
        manager.createNotificationChannel(channel)
    }

    fun getBitmapfromUrl(imageUrl: String?): Bitmap? {
        return try {
            val url = URL(imageUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input: InputStream = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
            null
        }
    }
}