package com.oho.oho.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.oho.oho.MainActivity;
import com.oho.oho.R;
import com.oho.oho.models.NotificationPreference;
import com.oho.oho.models.notifications.ChatNotificationPayload;
import com.oho.oho.models.notifications.LikeNotificationPayload;
import com.oho.oho.models.notifications.MatchNotificationPayload;
import com.oho.oho.utils.HelperClass;
import com.squareup.picasso.Picasso;

public class PushNotificationService extends FirebaseMessagingService {
    private HelperClass helperClass = new HelperClass();
    private NotificationPreference notificationPreference;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        // FCM registration token to server.
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        notificationPreference = helperClass.getNotificationPreference(getApplicationContext());

        Log.d("PushNotificationService", "data notification = " + message.getData());
        String notificationType = message.getData().get("type");

        switch (notificationType) {
            case "like":
                Log.d("PushNotificationService", "like notification preference for user = " + notificationPreference.getLikeNotification());
                if (notificationPreference.getLikeNotification()) {
                    String notificationBody = message.getData().get("sender_name") + " has liked your profile";
                    LikeNotificationPayload likeNotificationPayload = new LikeNotificationPayload("New Like!", notificationBody, message.getData().get("sender_name"), message.getData().get("sender_photo"), message.getData().get("user_id"), message.getData().get("type"));
                    sendLikeNotification(likeNotificationPayload);
                }
                break;
            case "chat":
                Log.d("PushNotificationService", "chat notification preference for user = " + notificationPreference.getChatNotification());
                if (notificationPreference.getChatNotification()) {
                    String notificationBody = "You have a new message from " + message.getData().get("sender_name");
                    ChatNotificationPayload chatNotificationPayload = new ChatNotificationPayload("New Message!", notificationBody, message.getData().get("sender_name"), message.getData().get("sender_photo"), message.getData().get("chat_id"), message.getData().get("channel_name"), message.getData().get("type"));
                    sendChatNotification(chatNotificationPayload);
                }
                break;
            case "match":
                Log.d("PushNotificationService", "chat notification preference for user = " + notificationPreference.getMatchNotification());
                if (notificationPreference.getMatchNotification()) {
                    String notificationBody = "You have matched with " + message.getData().get("sender_name");
                    MatchNotificationPayload matchNotificationPayload = new MatchNotificationPayload("New Match!", notificationBody, message.getData().get("sender_name"), message.getData().get("sender_photo"), message.getData().get("type"));
                    sendMatchNotification(matchNotificationPayload);
                }
                break;
        }
    }

    private void sendLikeNotification(LikeNotificationPayload notificationPayload) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Log.d("PushNotificationService", "data notification type inside = " + notificationPayload.getType());

        intent.putExtra("notificationPayload", notificationPayload);
        intent.putExtra("TYPE", "like");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);

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

        final Notification notification = notificationBuilder.build();

        notificationLayout.setTextViewText(R.id.textview_sender, "Oho!");
        notificationLayout.setTextViewText(R.id.textview_chat_message, "New Like!");

        notificationLayoutExpanded.setTextViewText(R.id.textview_notification_title, notificationPayload.getTitle());
        notificationLayoutExpanded.setTextViewText(R.id.textview_notification_message, notificationPayload.getBody());

        Log.d("PushNotificationservice", "notification sender's photo url: " + notificationPayload.getSenderPhoto());
        Uri uri = Uri.parse(notificationPayload.getSenderPhoto());
        Log.d("PushNotificationservice", "notification sender's photo uri: " + uri);
        notificationLayoutExpanded.setImageViewUri(R.id.imageview_notification_sender, uri);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        NotificationChannel channel = new NotificationChannel(channelId,
                "Oho notification channel",
                NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(channel);

        notificationManager.notify(0 /* ID of notification */, notification);

        Handler uiHandler = new Handler(Looper.getMainLooper());
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                String photoUrl = notificationPayload.getSenderPhoto() + ".jpeg";
                Log.d("PushNotificationService", "photo url of sender = " + photoUrl);
                Picasso
                        .get()
                        .load(photoUrl)
                        .into(notificationLayoutExpanded, R.id.imageview_notification_sender, 0, notification);
            }
        });
    }

    private void sendChatNotification(ChatNotificationPayload notificationPayload) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Log.d("PushNotificationService", "data notification type inside = " + notificationPayload.getType());
        intent.putExtra("notificationPayload", notificationPayload);
        intent.putExtra("TYPE", "chat");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
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

        final Notification notification = notificationBuilder.build();

        notificationLayout.setTextViewText(R.id.textview_sender, "Oho!");
        notificationLayout.setTextViewText(R.id.textview_chat_message, "New Message!");

        notificationLayoutExpanded.setTextViewText(R.id.textview_notification_title, notificationPayload.getTitle());
        notificationLayoutExpanded.setTextViewText(R.id.textview_notification_message, notificationPayload.getBody());

        Log.d("PushNotificationservice", "notification sender's photo url: " + notificationPayload.getSenderPhoto());
        Uri uri = Uri.parse(notificationPayload.getSenderPhoto());
        Log.d("PushNotificationservice", "notification sender's photo uri: " + uri);
        notificationLayoutExpanded.setImageViewUri(R.id.imageview_notification_sender, uri);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        NotificationChannel channel = new NotificationChannel(channelId,
                "Oho notification channel",
                NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(channel);

        notificationManager.notify(0 /* ID of notification */, notification);

        Handler uiHandler = new Handler(Looper.getMainLooper());
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                String photoUrl = notificationPayload.getSenderPhoto() + ".jpeg";
                Log.d("PushNotificationService", "photo url of sender = " + photoUrl);
                Picasso
                        .get()
                        .load(photoUrl)
                        .into(notificationLayoutExpanded, R.id.imageview_notification_sender, 0, notification);
            }
        });
    }

    private void sendMatchNotification(MatchNotificationPayload notificationPayload) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Log.d("PushNotificationService", "data notification type inside = " + notificationPayload.getType());
        intent.putExtra("notificationPayload", notificationPayload);
        intent.putExtra("TYPE", "match");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
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

        final Notification notification = notificationBuilder.build();

        notificationLayout.setTextViewText(R.id.textview_sender, "Oho!");
        notificationLayout.setTextViewText(R.id.textview_chat_message, "New Match!");

        notificationLayoutExpanded.setTextViewText(R.id.textview_notification_title, notificationPayload.getTitle());
        notificationLayoutExpanded.setTextViewText(R.id.textview_notification_message, notificationPayload.getBody());

        Log.d("PushNotificationservice", "notification sender's photo url: " + notificationPayload.getSenderPhoto());
        Uri uri = Uri.parse(notificationPayload.getSenderPhoto());
        Log.d("PushNotificationservice", "notification sender's photo uri: " + uri);
        notificationLayoutExpanded.setImageViewUri(R.id.imageview_notification_sender, uri);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        NotificationChannel channel = new NotificationChannel(channelId,
                "Oho notification channel",
                NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(channel);

        notificationManager.notify(0 /* ID of notification */, notification);

        Handler uiHandler = new Handler(Looper.getMainLooper());
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                String photoUrl = notificationPayload.getSenderPhoto() + ".jpeg";
                Log.d("PushNotificationService", "photo url of sender = " + photoUrl);
                Picasso
                        .get()
                        .load(photoUrl)
                        .into(notificationLayoutExpanded, R.id.imageview_notification_sender, 0, notification);
            }
        });
    }
}
