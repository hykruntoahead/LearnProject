package com.example.androidsearchchapter2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

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


    @Override
    protected void onResume() {
        super.onResume();
        recoverFromFile();
    }

    public void recoverFromFile(){
        new Thread(new Runnable() {
            @Override
            public void run() {
//                String path = getFilesDir().getPath().toString() + "/chapter2.txt";
                File chapterFile = new File(Constants.CHAPTER_2_PAYH);
                if (chapterFile.exists()){
                    ObjectInputStream objectInputStream = null;
                    try{
                        objectInputStream = new ObjectInputStream(new FileInputStream(chapterFile));
                        User user = (User) objectInputStream.readObject();
                        objectInputStream.close();
                        Log.d(TAG,"recover user:"+user);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


}
