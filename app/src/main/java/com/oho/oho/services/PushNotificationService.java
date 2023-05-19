package com.oho.oho.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.oho.oho.MainActivity;
import com.oho.oho.R;
import com.oho.oho.models.ChatNotificationPayload;

public class PushNotificationService extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        // FCM registration token to server.
        sendRegistrationToServer(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

        Log.d("PushNotificationService", "data notification = " + message.getData());

        if (message.getData().size() > 0) {
            ChatNotificationPayload notificationPayload = new ChatNotificationPayload();
            notificationPayload.setTitle(message.getData().get("title"));
            notificationPayload.setBody(message.getData().get("body"));
            notificationPayload.setChannelName(message.getData().get("channel_name"));
            notificationPayload.setChatId(message.getData().get("chat_id"));
            notificationPayload.setSenderName(message.getData().get("sender_name"));
            notificationPayload.setSenderPhoto(message.getData().get("sender_photo"));

            sendNotification(notificationPayload);
        }
    }

    private void sendNotification(ChatNotificationPayload notificationPayload) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("notificationPayload", notificationPayload);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_IMMUTABLE);

        String channelId = "fcm_default_channel";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // Layouts for the custom notification
        RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.notification_collapsed);
        RemoteViews notificationLayoutExpanded = new RemoteViews(getPackageName(), R.layout.notification_expanded);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(notificationPayload.getTitle())
                        .setContentText(notificationPayload.getBody())
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setCustomContentView(notificationLayout)
                        .setCustomBigContentView(notificationLayoutExpanded)
                        .setContentIntent(pendingIntent);

        notificationLayout.setTextViewText(R.id.textview_sender,notificationPayload.getSenderName());
        notificationLayout.setTextViewText(R.id.textview_chat_message,notificationPayload.getBody());

        notificationLayoutExpanded.setTextViewText(R.id.textview_notification_title,notificationPayload.getSenderName());
        notificationLayoutExpanded.setTextViewText(R.id.textview_notification_message,notificationPayload.getBody());

        Log.d("PushNotificationservice","notification sender's photo url: "+notificationPayload.getSenderPhoto());
        Uri uri = Uri.parse(notificationPayload.getSenderPhoto());
        Log.d("PushNotificationservice","notification sender's photo uri: "+uri);
        notificationLayoutExpanded.setImageViewUri(R.id.imageview_notification_sender,uri);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        NotificationChannel channel = new NotificationChannel(channelId,
                "Oho notification channel",
                NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(channel);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }


    private void sendRegistrationToServer(String token) {

    }
}
