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
import com.oho.oho.models.Profile;
import com.oho.oho.models.notifications.ChatNotificationPayload;
import com.oho.oho.models.notifications.FeedbackNotificationPayload;
import com.oho.oho.models.notifications.LikeNotificationPayload;
import com.oho.oho.models.notifications.MatchNotificationPayload;
import com.oho.oho.models.notifications.ReminderNotificationPayload;
import com.oho.oho.utils.HelperClass;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class PushNotificationService extends FirebaseMessagingService {
    private HelperClass helperClass = new HelperClass();
    private NotificationPreference notificationPreference;
    private Profile userProfile;

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
        userProfile = helperClass.getProfile(getApplicationContext());

        Log.d("PushNotificationService", "data notification = " + message.getData());
        String notificationType = message.getData().get("type");

        switch (notificationType) {
            case "like":
                Log.d("PushNotificationService", "like notification preference for user = " + notificationPreference.getLikeNotification());
                if (notificationPreference.getLikeNotification()) {
                    String notificationBody = message.getData().get("sender_name") + " has liked your profile.";
                    LikeNotificationPayload likeNotificationPayload = new LikeNotificationPayload("New Like!", notificationBody, message.getData().get("sender_name"), message.getData().get("sender_photo"), message.getData().get("user_id"), message.getData().get("type"));
                    sendLikeNotification(likeNotificationPayload);
                }
                break;
            case "chat":
                Log.d("PushNotificationService", "chat notification preference for user = " + notificationPreference.getChatNotification());
                if (notificationPreference.getChatNotification()) {
                    String notificationBody = "You have a new message from " + message.getData().get("sender_name") + ".";
                    ChatNotificationPayload chatNotificationPayload = new ChatNotificationPayload("New Message!", notificationBody, message.getData().get("sender_name"), message.getData().get("sender_photo"), message.getData().get("chat_id"), message.getData().get("channel_name"), message.getData().get("type"));
                    sendChatNotification(chatNotificationPayload);
                }
                break;
            case "match":
                Log.d("PushNotificationService", "chat notification preference for user = " + notificationPreference.getMatchNotification());
                if (notificationPreference.getMatchNotification()) {
                    String notificationBody = "You have matched with " + message.getData().get("sender_name") + ".";
                    MatchNotificationPayload matchNotificationPayload = new MatchNotificationPayload("New Match!", notificationBody, message.getData().get("sender_name"), message.getData().get("sender_photo"), message.getData().get("type"));
                    sendMatchNotification(matchNotificationPayload);
                }
                break;
            case "feedback":
                String sender_name;
                String sender_photo;
                if (Objects.equals(message.getData().get("user_a_name"), userProfile.getName()))
                    sender_name = message.getData().get("user_b_name");
                else
                    sender_name = message.getData().get("user_a_name");
                if (message.getData().get("user_a_pro_pic").equals(userProfile.getProfilePicture()))
                    sender_photo = message.getData().get("user_b_pro_pic");
                else
                    sender_photo = message.getData().get("user_a_pro_pic");

                Log.d("PushNotificationService", "logged in user name = " + userProfile.getName());
                Log.d("PushNotificationService", "sender user name = " + message.getData().get("user_a_name"));

                String notificationBody = "We're waiting for your feedback on your date with " + sender_name + ".";
                FeedbackNotificationPayload feedbackNotificationPayload = new FeedbackNotificationPayload("How was your last date?", notificationBody, Integer.parseInt(Objects.requireNonNull(message.getData().get("match_id"))), sender_name, sender_photo, message.getData().get("restaurant_name"), Integer.parseInt(Objects.requireNonNull(message.getData().get("reservation_time"))), Integer.parseInt(Objects.requireNonNull(message.getData().get("restaurant_id"))), message.getData().get("type"));
                sendFeedbackNotification(feedbackNotificationPayload);
                break;
            case "reminder":
                String sender_name_1;
                String sender_photo_1;
                if (Objects.equals(message.getData().get("user_a_name"), userProfile.getName()))
                    sender_name_1 = message.getData().get("user_b_name");
                else
                    sender_name_1 = message.getData().get("user_a_name");
                if (message.getData().get("user_a_pro_pic").equals(userProfile.getProfilePicture()))
                    sender_photo_1 = message.getData().get("user_b_pro_pic");
                else
                    sender_photo_1 = message.getData().get("user_a_pro_pic");

                Log.d("PushNotificationService", "logged in user name = " + userProfile.getName());
                Log.d("PushNotificationService", "sender user name = " + message.getData().get("user_a_name"));
                String notificationBody_1 = "You've a date tomorrow with " + sender_name_1 + " at " + message.getData().get("restaurant_name") + ".";
                ReminderNotificationPayload reminderNotificationPayload = new ReminderNotificationPayload("You've an upcoming date!", notificationBody_1, sender_name_1, sender_photo_1, message.getData().get("restaurant_name"), Integer.parseInt(Objects.requireNonNull(message.getData().get("reservation_time"))), Integer.parseInt(Objects.requireNonNull(message.getData().get("restaurant_id"))), message.getData().get("type"));
                sendReminderNotification(reminderNotificationPayload);
                break;
        }
    }

    private void sendLikeNotification(LikeNotificationPayload notificationPayload) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Log.d("PushNotificationService", "data notification type inside = " + notificationPayload.getType());

        intent.putExtra("notificationPayload", notificationPayload);
        intent.putExtra("TYPE", "like");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);

        String channelId = "fcm_default_channel";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // Layouts for the custom notification
        RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.notification_collapsed);
        RemoteViews notificationLayoutExpanded = new RemoteViews(getPackageName(), R.layout.notification_expanded);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId).setStyle(new NotificationCompat.DecoratedCustomViewStyle()).setSmallIcon(R.mipmap.ic_launcher).setContentTitle(notificationPayload.getTitle()).setContentText(notificationPayload.getBody()).setAutoCancel(true).setSound(defaultSoundUri).setCustomContentView(notificationLayout).setCustomBigContentView(notificationLayoutExpanded).setContentIntent(pendingIntent);

        final Notification notification = notificationBuilder.build();

        notificationLayout.setTextViewText(R.id.textview_sender, "Oho!");
        notificationLayout.setTextViewText(R.id.textview_chat_message, "New Like!");

        notificationLayoutExpanded.setTextViewText(R.id.textview_notification_title, notificationPayload.getTitle());
        notificationLayoutExpanded.setTextViewText(R.id.textview_notification_message, notificationPayload.getBody());

        Log.d("PushNotificationservice", "notification sender's photo url: " + notificationPayload.getSenderPhoto());
        Uri uri = Uri.parse(notificationPayload.getSenderPhoto());
        Log.d("PushNotificationservice", "notification sender's photo uri: " + uri);
        notificationLayoutExpanded.setImageViewUri(R.id.imageview_notification_sender, uri);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        NotificationChannel channel = new NotificationChannel(channelId, "Oho notification channel", NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(channel);

        notificationManager.notify(0 /* ID of notification */, notification);

        Handler uiHandler = new Handler(Looper.getMainLooper());
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                String photoUrl = notificationPayload.getSenderPhoto() + ".jpeg";
                Log.d("PushNotificationService", "photo url of sender = " + photoUrl);
                Picasso.get().load(photoUrl).into(notificationLayoutExpanded, R.id.imageview_notification_sender, 0, notification);
            }
        });
    }

    private void sendChatNotification(ChatNotificationPayload notificationPayload) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Log.d("PushNotificationService", "data notification type inside = " + notificationPayload.getType());
        intent.putExtra("notificationPayload", notificationPayload);
        intent.putExtra("TYPE", "chat");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
        String channelId = "fcm_default_channel";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        // Layouts for the custom notification
        RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.notification_collapsed);
        RemoteViews notificationLayoutExpanded = new RemoteViews(getPackageName(), R.layout.notification_expanded);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId).setStyle(new NotificationCompat.DecoratedCustomViewStyle()).setSmallIcon(R.mipmap.ic_launcher).setContentTitle(notificationPayload.getTitle()).setContentText(notificationPayload.getBody()).setAutoCancel(true).setSound(defaultSoundUri).setCustomContentView(notificationLayout).setCustomBigContentView(notificationLayoutExpanded).setContentIntent(pendingIntent);

        final Notification notification = notificationBuilder.build();

        notificationLayout.setTextViewText(R.id.textview_sender, "Oho!");
        notificationLayout.setTextViewText(R.id.textview_chat_message, "New Message!");

        notificationLayoutExpanded.setTextViewText(R.id.textview_notification_title, notificationPayload.getTitle());
        notificationLayoutExpanded.setTextViewText(R.id.textview_notification_message, notificationPayload.getBody());

        Log.d("PushNotificationservice", "notification sender's photo url: " + notificationPayload.getSenderPhoto());
        Uri uri = Uri.parse(notificationPayload.getSenderPhoto());
        Log.d("PushNotificationservice", "notification sender's photo uri: " + uri);
        notificationLayoutExpanded.setImageViewUri(R.id.imageview_notification_sender, uri);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        NotificationChannel channel = new NotificationChannel(channelId, "Oho notification channel", NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(channel);

        notificationManager.notify(0 /* ID of notification */, notification);

        Handler uiHandler = new Handler(Looper.getMainLooper());
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                String photoUrl = notificationPayload.getSenderPhoto() + ".jpeg";
                Log.d("PushNotificationService", "photo url of sender = " + photoUrl);
                Picasso.get().load(photoUrl).into(notificationLayoutExpanded, R.id.imageview_notification_sender, 0, notification);
            }
        });
    }

    private void sendMatchNotification(MatchNotificationPayload notificationPayload) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Log.d("PushNotificationService", "data notification type inside = " + notificationPayload.getType());
        intent.putExtra("notificationPayload", notificationPayload);
        intent.putExtra("TYPE", "match");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
        String channelId = "fcm_default_channel";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        // Layouts for the custom notification
        RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.notification_collapsed);
        RemoteViews notificationLayoutExpanded = new RemoteViews(getPackageName(), R.layout.notification_expanded);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId).setStyle(new NotificationCompat.DecoratedCustomViewStyle()).setSmallIcon(R.mipmap.ic_launcher).setContentTitle(notificationPayload.getTitle()).setContentText(notificationPayload.getBody()).setAutoCancel(true).setSound(defaultSoundUri).setCustomContentView(notificationLayout).setCustomBigContentView(notificationLayoutExpanded).setContentIntent(pendingIntent);

        final Notification notification = notificationBuilder.build();

        notificationLayout.setTextViewText(R.id.textview_sender, "Oho!");
        notificationLayout.setTextViewText(R.id.textview_chat_message, "New Match!");

        notificationLayoutExpanded.setTextViewText(R.id.textview_notification_title, notificationPayload.getTitle());
        notificationLayoutExpanded.setTextViewText(R.id.textview_notification_message, notificationPayload.getBody());

        Log.d("PushNotificationservice", "notification sender's photo url: " + notificationPayload.getSenderPhoto());
        Uri uri = Uri.parse(notificationPayload.getSenderPhoto());
        Log.d("PushNotificationservice", "notification sender's photo uri: " + uri);
        notificationLayoutExpanded.setImageViewUri(R.id.imageview_notification_sender, uri);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        NotificationChannel channel = new NotificationChannel(channelId, "Oho notification channel", NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(channel);

        notificationManager.notify(0 /* ID of notification */, notification);

        Handler uiHandler = new Handler(Looper.getMainLooper());
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                String photoUrl = notificationPayload.getSenderPhoto() + ".jpeg";
                Log.d("PushNotificationService", "photo url of sender = " + photoUrl);
                Picasso.get().load(photoUrl).into(notificationLayoutExpanded, R.id.imageview_notification_sender, 0, notification);
            }
        });
    }

    private void sendFeedbackNotification(FeedbackNotificationPayload notificationPayload) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Log.d("PushNotificationService", "data notification type inside = " + notificationPayload.getType());
        intent.putExtra("notificationPayload", notificationPayload);
        intent.putExtra("TYPE", "feedback");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
        String channelId = "fcm_default_channel";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        // Layouts for the custom notification
        RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.notification_collapsed);
        RemoteViews notificationLayoutExpanded = new RemoteViews(getPackageName(), R.layout.notification_expanded);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId).setStyle(new NotificationCompat.DecoratedCustomViewStyle()).setSmallIcon(R.mipmap.ic_launcher).setContentTitle(notificationPayload.getTitle()).setContentText(notificationPayload.getBody()).setAutoCancel(true).setSound(defaultSoundUri).setCustomContentView(notificationLayout).setCustomBigContentView(notificationLayoutExpanded).setContentIntent(pendingIntent);

        final Notification notification = notificationBuilder.build();

        notificationLayout.setTextViewText(R.id.textview_sender, "Oho!");
        notificationLayout.setTextViewText(R.id.textview_chat_message, "How was your last date?");

        notificationLayoutExpanded.setTextViewText(R.id.textview_notification_title, notificationPayload.getTitle());
        notificationLayoutExpanded.setTextViewText(R.id.textview_notification_message, notificationPayload.getBody());

        Log.d("PushNotificationservice", "notification sender's photo url: " + notificationPayload.getSenderPhoto());
        Uri uri = Uri.parse(notificationPayload.getSenderPhoto());
        Log.d("PushNotificationservice", "notification sender's photo uri: " + uri);
        notificationLayoutExpanded.setImageViewUri(R.id.imageview_notification_sender, uri);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        NotificationChannel channel = new NotificationChannel(channelId, "Oho notification channel", NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(channel);

        notificationManager.notify(0 /* ID of notification */, notification);

        Handler uiHandler = new Handler(Looper.getMainLooper());
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                String photoUrl = notificationPayload.getSenderPhoto() + ".jpeg";
                Log.d("PushNotificationService", "photo url of sender = " + photoUrl);
                Picasso.get().load(photoUrl).into(notificationLayoutExpanded, R.id.imageview_notification_sender, 0, notification);
            }
        });
    }

    private void sendReminderNotification(ReminderNotificationPayload notificationPayload) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Log.d("PushNotificationService", "data notification type inside = " + notificationPayload.getType());
        intent.putExtra("notificationPayload", notificationPayload);
        intent.putExtra("TYPE", "reminder");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
        String channelId = "fcm_default_channel";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        // Layouts for the custom notification
        RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.notification_collapsed);
        RemoteViews notificationLayoutExpanded = new RemoteViews(getPackageName(), R.layout.notification_expanded);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId).setStyle(new NotificationCompat.DecoratedCustomViewStyle()).setSmallIcon(R.mipmap.ic_launcher).setContentTitle(notificationPayload.getTitle()).setContentText(notificationPayload.getBody()).setAutoCancel(true).setSound(defaultSoundUri).setCustomContentView(notificationLayout).setCustomBigContentView(notificationLayoutExpanded).setContentIntent(pendingIntent);

        final Notification notification = notificationBuilder.build();

        notificationLayout.setTextViewText(R.id.textview_sender, "Oho!");
        notificationLayout.setTextViewText(R.id.textview_chat_message, "You've an upcoming date!");

        notificationLayoutExpanded.setTextViewText(R.id.textview_notification_title, notificationPayload.getTitle());
        notificationLayoutExpanded.setTextViewText(R.id.textview_notification_message, notificationPayload.getBody());

        Log.d("PushNotificationservice", "notification sender's photo url: " + notificationPayload.getSenderPhoto());
        Uri uri = Uri.parse(notificationPayload.getSenderPhoto());
        Log.d("PushNotificationservice", "notification sender's photo uri: " + uri);
        notificationLayoutExpanded.setImageViewUri(R.id.imageview_notification_sender, uri);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        NotificationChannel channel = new NotificationChannel(channelId, "Oho notification channel", NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(channel);

        notificationManager.notify(0 /* ID of notification */, notification);

        Handler uiHandler = new Handler(Looper.getMainLooper());
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                String photoUrl = notificationPayload.getSenderPhoto() + ".jpeg";
                Log.d("PushNotificationService", "photo url of sender = " + photoUrl);
                Picasso.get().load(photoUrl).into(notificationLayoutExpanded, R.id.imageview_notification_sender, 0, notification);
            }
        });
    }
}
