package com.example.androidsearchchapter2.socketipc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * Created by heyukun on 2017/7/19.
 */

public class TCPServerService extends Service {
    private static final String TAG = "TCPServerService";

    private boolean mIsServiceDestroyed = false;
    private String[] mDefinedMessages = new String[]{
            "你好啊！哈哈",
            "请问你叫什么名字呀？",
            "今天杭州天气不错啊，shy",
            "你知道吗？我可是可以和多个人同时聊天的哦",
            "给你讲个笑话吧：据说爱笑的人运气不会太差，不知道真假。"
    };

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new TcpService()).start();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsServiceDestroyed = true;
    }

    private class TcpService implements Runnable{

        @Override
        public void run() {
            ServerSocket serverSocket = null;

            try {
                serverSocket = new ServerSocket(8688);
            } catch (IOException e) {
                System.err.println("establish tcp server failed ,port :8688");
                e.printStackTrace();
                return;
            }

            while (!mIsServiceDestroyed){
                //接受客户端请求
                try {
                    final Socket client = serverSocket.accept();
                    Log.d(TAG,"accept");
                    new Thread(){
                        public void run(){
                            try {
                                responseClient(client);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
    }

    private void responseClient(Socket client)  throws IOException{
        //用于接受客户端消息
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        //用于向服务端发送消息
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())),true);
        out.println("欢迎来到聊天室!");
        while (!mIsServiceDestroyed){
            String str = in.readLine();
            Log.d(TAG,"msg from client:"+str);
            if (str == null){
                //客户端断开连接
                break;
            }
            int i = new Random().nextInt(mDefinedMessages.length);
            String msg = mDefinedMessages[i];
            out.println(msg);
            System.out.println("send :"+msg);
        }
        Log.d(TAG,"client quit");
        //关闭
        out.close();
        in.close();
        client.close();

    }

}
