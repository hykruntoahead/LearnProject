package com.example.androidsearchchapter2;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * Created by heyukun on 2017/7/15.
 */

public class MyApplication extends Application {
    private static final String TAG = "progress-MyApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        progressNameLog();
    }

    private void progressNameLog(){
        int pid = android.os.Process.myPid();
        String processName = "";
        ActivityManager manager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo process: manager.getRunningAppProcesses()) {
            if(process.pid == pid)
            {
                processName = process.processName;
            }
        }
        Log.i(TAG,processName);
    }


}
