package com.example.retrofit2learn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity.class-";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .build();
        final GitHubApi repo = retrofit.create(GitHubApi.class);

        Call<ResponseBody> call = repo.contributorsBySimpleGetCall("square", "retrofit");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String str = null;
                try {
                    str = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d(TAG,str);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG,t.getMessage());
            }
        });


    }

    /**
     * RESTful:
     * REST(REpresentational State Transfer)是一组架构约束条件和原则。
     RESTful架构都满足以下规则：
     （1）每一个URI代表一种资源；
     （2）客户端和服务器之间，传递这种资源的某种表现层；
     （3）客户端通过四个HTTP动词，对服务器端资源进行操作，实现"表现层状态转化"。
     */
}
