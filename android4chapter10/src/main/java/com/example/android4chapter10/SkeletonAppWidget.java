package com.example.android4chapter10;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Created by heyukun on 2017/7/12.
 */

//widget 是作为Broadcast Receiver 实现的
public class SkeletonAppWidget extends AppWidgetProvider {
    public static final String WIDGET_BTN_ACTION = "widget_btn_action";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (intent != null && TextUtils.equals(intent.getAction(), WIDGET_BTN_ACTION)) { //当intent不为空，且action匹配成功时，就接收广播，然后点击事件成功
            Log.i(WIDGET_BTN_ACTION, "is clicked");
            //接下来开始做点击事件里面的内容
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_ll);
            //注意：需要【重新】构造一个RemoteViews
            remoteViews.setTextViewText(R.id.widget_text, "be clicked");
            remoteViews.setTextColor(R.id.widget_text, Color.RED);

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);// 单例模式
            ComponentName componentName = new ComponentName(context, SkeletonAppWidget.class);
            appWidgetManager.updateAppWidget(componentName, remoteViews);//setText之后，记得更新一下
        }
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_ll);//需要构造一个RemoteViews

        Intent intent = new Intent();
        intent.setClass(context, SkeletonAppWidget.class); //通过intent把广播发给TestWidget本身，TestWidget接受到广播之后，会调用onReceive()方法进而刷新界面。
        intent.setAction(WIDGET_BTN_ACTION);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        remoteViews.setOnClickPendingIntent(R.id.widget_image, pendingIntent);//控件btn_widget的点击事件：点击按钮时，会发一个带action的广播。

        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews); //点击完了之后，记得更新一下。
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        // TODO: 2017/7/12  处理删除widget的操作 
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        // TODO: 2017/7/12 Widget 已被禁用 
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        // TODO: 2017/7/12 widget 已被启用 
    }
}
