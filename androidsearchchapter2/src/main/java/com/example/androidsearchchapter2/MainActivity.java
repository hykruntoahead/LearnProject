package com.example.androidsearchchapter2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "progress-MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String filePath = getFilesDir().getPath().toString() + "/cache.txt";
        final File f = new File(filePath);

        findViewById(R.id.btn_act2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SecondActivity.class));
            }
        });
        UserManager.sUSerId++;
        Log.i(TAG,"Run MainActivity onCreate useId="+UserManager.sUSerId);
        findViewById(R.id.btn_ser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(0,"jake",true);
                try{
                    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
                    out.writeObject(user);
                    out.close();
                    Log.i(TAG,"Run MainActivity writeObject");
                }catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.btn_reser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ObjectInputStream in = new ObjectInputStream(new FileInputStream(f));
                    User newUser = (User) in.readObject();
                    in.close();
                    Log.i(TAG,"Run MainActivity user:"+newUser.toString());
                }
                catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
