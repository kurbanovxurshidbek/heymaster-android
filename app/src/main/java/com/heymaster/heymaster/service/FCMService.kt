package com.heymaster.heymaster.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.heymaster.heymaster.R
import com.heymaster.heymaster.SharedPref
import com.heymaster.heymaster.role.client.ClientActivity
import com.heymaster.heymaster.role.master.MasterActivity
import com.heymaster.heymaster.ui.auth.LoginActivity
import com.heymaster.heymaster.utils.Constants.CLIENT
import com.heymaster.heymaster.utils.Constants.KEY_USER_ROLE
import com.heymaster.heymaster.utils.Constants.MASTER


const val channelId = "notification_channel"
const val channelName = "com.heymaster.heymaster.service"

class FCMService : FirebaseMessagingService() {
    val TAG = FCMService::class.java.simpleName

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Refreshed token :: $token")
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String) {
        // TODO : send token to tour server
    }

    override fun onMessageReceived(message: RemoteMessage) {

        if (message.notification != null) {
            generateNotification(message.notification!!.title!!, message.notification!!.body!!)
        }

    }

    fun getRemoteView(title: String, body: String): RemoteViews {
        val remoteView = RemoteViews("com.heymaster.heymaster", R.layout.notification)

        remoteView.setTextViewText(R.id.title, title)
        remoteView.setTextViewText(R.id.body, body)
        remoteView.setImageViewResource(R.id.app_logo, R.drawable.app_logo)

        return remoteView
    }


    private fun generateNotification(title: String, body: String) {
        val intent = if (SharedPref(this).getString(KEY_USER_ROLE) == MASTER) {
            Intent(this, MasterActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .putExtra("key", MASTER)

        } else if (SharedPref(this).getString(KEY_USER_ROLE) == CLIENT){
            Intent(this, ClientActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .putExtra("key", CLIENT)
        } else {
            Intent(this, LoginActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                .putExtra("key", "unknown")
        }



        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        var builder: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.app_logo)
            .setAutoCancel(true)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setVibrate(longArrayOf(1000,1000,1000,1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        builder = builder.setContent(getRemoteView(title, body))

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(0, builder.build())

    }
}