package com.deledzis.localshare.api

import android.app.PendingIntent
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.deledzis.localshare.App
import com.deledzis.localshare.R
import com.deledzis.localshare.ui.main.MainActivity
import com.deledzis.localshare.util.logi
import com.deledzis.localshare.util.logw
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.*

class FcmService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        logi("FcmService", "Refreshed token: $token")
    }

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // There are two types of messages data messages and notification messages.
        // Data messages are handled here in onMessageReceived whether the app is in the foreground or background.
        // Data messages are the type traditionally used with FCM.
        // Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated
        // notification is displayed.
        // When the user taps on the notification they are returned to the app.
        // Messages containing both notification and data payloads are treated as notification messages.
        // The Firebase console always sends notification messages.
        // For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        logw("FcmService", "From: ${remoteMessage.from}")

        // Check if message contains a data payload.
        val data = remoteMessage.data
        if (data.isNotEmpty()) {
            logw("FcmService", "Message data payload: $data")
            // Send a notification that you got a new message
            val title = if (data.containsKey("title")) data["title"]!! else "Новое уведомление"
            val message = if (data.containsKey("message")) data["message"]!! else ""
            sendNotification(title, message)
        } else {
            // Check if message contains a notification data.
            val notification = remoteMessage.notification
            if (notification != null) {
                logw("FcmService", "Message notification: $notification")
                // Send a notification that you got a new message
                val title = notification.title ?: "Новое уведомление"
                val message = notification.body ?: ""
                sendNotification(title, message)
            }
        }
    }

    /**
     * Create and show a simple notification containing the received FCM message
     */
    private fun sendNotification(title: String, message: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        val defaultSoundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder: NotificationCompat.Builder
                = NotificationCompat.Builder(this, App.injector.context().getString(R.string.default_notification_channel_id))
                // TODO: create app icon
//            .setSmallIcon(R.drawable.ic_logo_source)
            .setContentTitle(title)
            // If the message is longer than the max number of characters we want in our
            // notification, truncate it and add the unicode character for ellipsis
            .setContentText(
                if (message.length > NOTIFICATION_MAX_CHARACTERS)
                    message.substring(0, NOTIFICATION_MAX_CHARACTERS) + "\u2026"
                else
                    message
            )
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        with(NotificationManagerCompat.from(this)) {
            notify(Calendar.getInstance().timeInMillis.toInt(), notificationBuilder.build())
        }
    }

    companion object {
        const val NOTIFICATION_MAX_CHARACTERS = 128
    }
}