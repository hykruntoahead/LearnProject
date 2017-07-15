package com.example.androidsearchchapter2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by heyukun on 2017/7/14.
 */

public class ThirdActivity extends AppCompatActivity {
    public static final String TAG = "progress-ThirdActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_third);
        Log.i(TAG,"Run ThirdActivity onCreate useId="+UserManager.sUSerId);
    }
}
