package com.example.android4chapter10;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取NotificationManager对象
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        findViewById(R.id.btn_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast();
            }
        });
        findViewById(R.id.btn_notifica).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotification();
            }
        });
        findViewById(R.id.btn_notifica2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotification2();
            }
        });
        findViewById(R.id.btn_notifica3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotification3();
            }
        });
    }

    private void showToast(){
        String msg = "Cheers!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getApplicationContext(),msg,duration);
        toast.setGravity(Gravity.TOP,0,0);

        LinearLayout ll = new LinearLayout(getApplicationContext());
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.colorGray));

        TextView myTextView = new TextView(getApplicationContext());
        myTextView.setText(msg);

        int lHeight = LinearLayout.LayoutParams.MATCH_PARENT;
        int lWidth = LinearLayout.LayoutParams.WRAP_CONTENT;

        ll.addView(myTextView,new LinearLayout.LayoutParams(lHeight,lWidth));

        ll.setPadding(50,20,20,50);

        toast.setView(ll);
        toast.show();
    }


    //创建并展示Notification
    private void showNotification(){
        //图标
        int icon = R.mipmap.ic_launcher;
        //当启动通知时在状态栏中显示文本
        String tickerText = "Notification";

        //展开的状态栏按时间顺序排序通知
        long when = System.currentTimeMillis();
         int  NOTIFICATION_REF = 2;
        //过期
        Notification notification = new Notification(icon,tickerText,when);
        notificationManager.notify(NOTIFICATION_REF,notification);
    }


    //带进度条的Notification
    private void showNotification3(){
        Notification.Builder builder = new Notification.Builder(this);

        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("通知")
                .setContentText("Progress")
                .setWhen(System.currentTimeMillis())
                .setProgress(100,10,false)
                .setAutoCancel(true);


        Notification notification = builder.build();
        int  NOTIFICATION_REF = 4;
        notificationManager.notify(NOTIFICATION_REF,notification);
    }

    /*
      知识点：
      使用默认的Notification:
         Notification.DEFAULT_LIGHTS
         Notification.DEFAULT_SOUND
         Notification.DEFAULT_VIBRATE

         eg--声音震动：
         notification.default =   Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE
         如果全使用默认值:
         notification.default =  Notification.DEFAULT_ALL

     */

    //使用Notification Build

    private void showNotification2(){
        Notification.Builder builder = new Notification.Builder(this);

        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("通知")
                .setContentText("Notification")
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[]{1000,1000,1000,1000})
                .setLights(Color.RED,0,1);

        Notification notification = builder.build();
        int  NOTIFICATION_REF = 3;
        notificationManager.notify(NOTIFICATION_REF,notification);
    }

}
