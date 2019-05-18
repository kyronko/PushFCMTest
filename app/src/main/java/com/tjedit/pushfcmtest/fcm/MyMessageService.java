package com.tjedit.pushfcmtest.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.tjedit.pushfcmtest.MainActivity;
import com.tjedit.pushfcmtest.R;

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

        NotificationCompat.Builder builder = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            안드로이드 버젼 코드가  보다 크ㅏ! 오레오 이상의 최신버젼이다.
//            알림채널을 만들어서 해당 채널에 푸쉬알림을 던져주는 방식
//            알림 채널을 생성. 중요도를 보통으로 세팅
            NotificationChannel channel = new NotificationChannel("app name","app name",NotificationManager.IMPORTANCE_DEFAULT);
//            해당채널에 대한 설명문
            channel.setDescription("푸쉬 알림 테스트용 채널입니다.");
//            채널에서 조명을 사용할지
            channel.enableLights(true);
//            색깔은 뭘로
            channel.setLightColor(Color.GREEN);
//            진돌 소리는 다시 한번 열어줘야함
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1,1000});
            channel.setSound(defaultNotiUri,null);
//            잠금화면에서 보여줄지
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            notificationManager.createNotificationChannel(channel);
         //   알림채널이라는 기능이 없어서 그냥 통일해서 쓰면 앱이 죽어버림.
            builder = new NotificationCompat.Builder(this,channel.getId());
//            푸쉬알림 상태창에 드는 아이콘 설정
            builder.setSmallIcon(R.mipmap.ic_launcher);
//            알림제목설정
            builder.setContentTitle(title);
//            알림의 내용설정
            builder.setContentTitle(content);
//            알림의 울림소리설정
            builder.setSound(defaultNotiUri);
//            알림이 왔을때 울리는 진동의 패턴
            builder.setVibrate(new long[]{1,1000});

//            자동삭제가 되도록
            builder.setAutoCancel(true);
//            알림을 누르면 어디로 갈지
            builder.setContentIntent(pendingIntent);
//            실제로 알림을 띄우는 부분
//            id를 일반숫자로 고정하면 항상 같은 id가 대입 = > 여러번 알림이 오면 기존알림을 덮어씀
//            만약 여러개의 알림을 모두 띄우고 싶다면 그때그때 다른 숫자가 들어가도록 코딩
            notificationManager.notify(1,builder.build());

}
        else{
//            오레오 보다는 밑의 버젼이다. N버젼 이하.
//            알림채널이라는 기능이 없어서 그냥 통일해서 쓰면 앱이 죽어버림.
            builder = new NotificationCompat.Builder(this);
//            푸쉬알림 상태창에 드는 아이콘 설정
            builder.setSmallIcon(R.mipmap.ic_launcher);
//            알림제목설정
            builder.setContentTitle(title);
//            알림의 내용설정
            builder.setContentTitle(content);
//            알림의 울림소리설정
            builder.setSound(defaultNotiUri);
//            알림이 왔을때 울리는 진동의 패턴
            builder.setVibrate(new long[]{1,1000});

//            자동삭제가 되도록
            builder.setAutoCancel(true);
//            알림을 누르면 어디로 갈지
            builder.setContentIntent(pendingIntent);
//            실제로 알림을 띄우는 부분
//            id를 일반숫자로 고정하면 항상 같은 id가 대입 = > 여러번 알림이 오면 기존알림을 덮어씀
//            만약 여러개의 알림을 모두 띄우고 싶다면 그때그때 다른 숫자가 들어가도록 코딩
            notificationManager.notify(1,builder.build());



        }

    }

}
