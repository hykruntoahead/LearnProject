package com.example.android_search_chapter1;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.android4chapter11.R;



/**
 *onCreate - onDestroy 生存
 * onStart - onStop 可见
 * onResume - onPause 前台
 *
 */


public class MainActivity extends AppCompatActivity {
    public static final String TAG = "learn- MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG,"onCreate");
        findViewById(R.id.btn_to_act2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * MainActivity --> SecondActivity
                 * 1.Main onPause
                 * 2.Second onCreate->onStart->onResume
                 * 3.Main onStop
                 *
                 * ps: 旧的Activity先onPause 然后新的Activity启动
                 */
                startActivity(new Intent(MainActivity.this,SecondActivity.class));
            }
        });


        findViewById(R.id.btn_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,MainActivity.class);
                intent.putExtra("time",System.currentTimeMillis());
                startActivity(intent);
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    /**
     * 可做一些存储数据／停止动画等工作，不能太耗时
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"onPause");
    }

    /**
     * 可以做一些稍微重量级的回收工作 同样不能太耗时
     * PS:当打开新Activity（采用透明主题），那么当前activity不会回调onStop
     */
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     *
     * @param outState
     * Activity 异常终止情况下调用
     * 用以保存当前Activity的状态
     * 调用时机 在onStop 之前 和onPause 没有既定时需关系
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG,"onSaveInstanceState");
        outState.putString("extra_test","test");
    }

    /**
     *
     * @param savedInstanceState
     * 当Activity异常终止重新创建时 调用
     * 调用时机 在onStart 后
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String test = savedInstanceState.getString("extra_test");
        Log.d(TAG,"onRestoreInstanceState extra_test:"+test);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG,"onConfigurationChanged:"+newConfig.orientation);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG,"onNewIntent,time="+intent.getLongExtra("time",0));
    }
}
