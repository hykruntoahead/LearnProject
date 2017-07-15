package com.example.androidsearchchapter2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/**
 * Created by heyukun on 2017/7/14.
 */

public class SecondActivity extends AppCompatActivity {
    public static final String TAG = "progress-SecondActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_second);
        findViewById(R.id.btn_act3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SecondActivity.this,ThirdActivity.class));
            }
        });
        Log.i(TAG,"Run SecondActivity onCreate useId="+UserManager.sUSerId);
        UserManager.sUSerId ++;
    }
}
