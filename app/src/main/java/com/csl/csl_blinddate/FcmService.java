package com.csl.csl_blinddate;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FcmService extends FirebaseMessagingService {
    private final String GROUP_KEY = "FCM_KEY";

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("auto_login",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("token",s);
        editor.commit();// 첫 토큰 생성시 인스턴스 에 토큰값 넣어주기
        Log.d("Fcm_token",s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);


        String title = remoteMessage.getData().get("title");//firebase에서 보낸 메세지의 title
        String message = remoteMessage.getData().get("message");//firebase에서 보낸 메세지의 내용
        String temp = remoteMessage.getData().get("flag");

        int flag = Integer.parseInt(temp);

        Intent intent;
        if(flag == 0) {
            intent = new Intent(this,ApplyedListActivity.class);
        }
        else if (flag == 1) {
            intent = new Intent(this,ApplyListActivity.class);
        }
        else if (flag == 2){
            intent = new Intent(this,MainActivity.class);
            intent.putExtra("fragment","chat");
        }
        else {
            intent = new Intent();
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(this, (int)System.currentTimeMillis()/1000, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String channel = "채널";
            String channel_nm = "채널명";
            //NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationManager notichannel = (android.app.NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationChannel channelMessage = new NotificationChannel(channel, channel_nm,
                    android.app.NotificationManager.IMPORTANCE_DEFAULT);
            channelMessage.setDescription("채널에 대한 설명.");
            channelMessage.enableLights(true);
            channelMessage.enableVibration(true);
            channelMessage.setShowBadge(false);
            channelMessage.setVibrationPattern(new long[]{1000, 1000});
            notichannel.createNotificationChannel(channelMessage);

            NotificationCompat.Builder groupBuilder =
                    new NotificationCompat.Builder(this, channel)
                            .setSmallIcon(R.drawable.ic_launcher_background)
                            .setContentTitle("Group")
                            .setContentText("group test")
                            .setGroupSummary(true)
                            .setChannelId(channel)
                            .setGroup(GROUP_KEY)
                            .setContentIntent(pendingIntent);

            //푸시알림을 Builder를 이용하여 만듭니다.
            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(this, channel)
                            .setSmallIcon(R.drawable.ic_launcher_background)
                            .setContentTitle(title)//푸시알림의 제목
                            .setContentText(message)//푸시알림의 내용
                            .setChannelId(channel)
                            .setGroup(GROUP_KEY)
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true)
                            .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);

            NotificationManagerCompat manager = NotificationManagerCompat.from(this);
            manager.notify(1,groupBuilder.build());
            manager.notify((int)System.currentTimeMillis()/1000,notificationBuilder.build());

        } else {
            NotificationCompat.Builder groupBuilder =
                    new NotificationCompat.Builder(this, "")
                            .setSmallIcon(R.drawable.ic_launcher_background)
                            .setContentTitle("Group")
                            .setContentText("group test")
                            .setGroupSummary(true)
                            .setGroup(GROUP_KEY)
                            .setContentIntent(pendingIntent);

            //푸시알림을 Builder를 이용하여 만듭니다.
            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(this, "")
                            .setSmallIcon(R.drawable.ic_launcher_background)
                            .setContentTitle(title)//푸시알림의 제목
                            .setContentText(message)//푸시알림의 내용
                            .setGroup(GROUP_KEY)
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true)
                            .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);

            NotificationManagerCompat manager = NotificationManagerCompat.from(this);
            manager.notify(1,groupBuilder.build());
            manager.notify((int)System.currentTimeMillis()/1000,notificationBuilder.build());

        }
    }
}
