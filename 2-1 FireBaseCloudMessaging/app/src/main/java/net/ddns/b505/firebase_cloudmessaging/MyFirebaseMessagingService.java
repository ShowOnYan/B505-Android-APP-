package net.ddns.b505.firebase_cloudmessaging;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Set;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData().size() > 0) {
            //Log.d("FCM", "Message data payload: " + remoteMessage.getData());
            Set<String> keys = remoteMessage.getData().keySet();
            for(String s:keys) {
                Log.e("fcm data:", remoteMessage.getData().get(s));
            }
            //TODO Handle the data
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.i("MyFirebaseService","title "+remoteMessage.getNotification().getTitle());
            Log.i("MyFirebaseService","body "+remoteMessage.getNotification().getBody());
            //Log.d("FCM", "Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
        }

    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.i("MyFirebaseService","token "+s);
    }
    private void sendNotification(String messageTitle, String messageBody) {
        //點開通知會跳轉到該網址
        //Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.baidu.com"));
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= 26)
        {
            //當sdk版本大於26
            String id = "channel_1";//channel的id
            String description = "143";//channel的描述訊息
            int importance = NotificationManager.IMPORTANCE_LOW;//channel的重要性
            NotificationChannel channel = new NotificationChannel(id, description, importance);
            //生成channel
            //為channel添加屬性
            //channel.enableVibration(true); 震動
            //channel.enableLights(true);  提示燈
            manager.createNotificationChannel(channel);
            Notification notification = new Notification.Builder(this, id)
                    //注意這邊多了一個參數id，指配置的NotificationChannel的id
                    //你可以自己去試一下 運行一次後 即配置完後 將這行代碼以上的代
                    //碼註解掉 將参数id直接改成“channel_1”也可以成功運行
                    //但改成别的如“channel_2”就不行了
                    .setCategory(Notification.CATEGORY_MESSAGE)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(messageTitle)
                    .setContentText(messageBody)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build();
            manager.notify(1, notification);
        }
        else
        {
            //當sdk版本小於26
            Notification notification = new NotificationCompat.Builder(this)
                    .setContentTitle(messageTitle)
                    .setContentText(messageBody)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .build();
            manager.notify(1,notification);
        }
    }
}
