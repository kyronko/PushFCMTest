package com.tjedit.pushfcmtest.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.tjedit.pushfcmtest.MainActivity;

public class MyMessageService extends FirebaseMessagingService {
    public void onNewToken(String s){
        super.onNewToken(s);
        Log.d("발급된 토큰",s);


    }

    @Override
    public  void  onMessageReceived(RemoteMessage remoteMessage){
        String pushTitle = remoteMessage.getNotification().getTitle();
        String pushBody = remoteMessage.getNotification().getBody();
        Log.d("푸쉬알림제목",pushTitle);
        Log.d("푸쉬알림바디",pushBody);
    }
    void showNotification(String title , String content){
        Intent intent = new Intent(MyMessageService.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0, intent,PendingIntent.FLAG_ONE_SHOT);
//         Uri : 어딘가로 향하는 경로를 지정할때 자주사용
        Uri defaultNotiUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//       알림을 띄우는 역할을 담당하는 매니저를 안드로이드 시스템으로부터 얻어옴
//        안드로이드 시스템서비스는 여러가지의 종류를 내포 . 이번에 쓰는건 알림이라고 반드시 강제 캐스팅
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);


    }

}
