package com.example.androidsearchchapter2.socketipc;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.androidsearchchapter2.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;

/**
 * Created by heyukun on 2017/7/19.
 */

public class TcpClientActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "TcpClientActivity";


    private static final int MESSAGE_REXEIVE_NEW_MSG = 1;
    private static final int MESSAGE_SOCKET_CONNECTED = 2;

    private Button mSendButton;
    private TextView mMessageTextView;
    private EditText mMessageEditText;

    private PrintWriter mPrintWriter;
    private Socket mClientSocket;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MESSAGE_REXEIVE_NEW_MSG:
                    mMessageTextView.setText(mMessageTextView.getText() + (String)msg.obj);
                    break;
                case  MESSAGE_SOCKET_CONNECTED:
                    mSendButton.setEnabled(true);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_tcpclient);
        mMessageTextView = (TextView) findViewById(R.id.textView);
        mSendButton = (Button) findViewById(R.id.button);
        mMessageEditText = (EditText) findViewById(R.id.editText);

        mSendButton.setOnClickListener(this);

        Intent intent = new Intent(this,TCPServerService.class);
        startService(intent);

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        connectTCPServer();
                    }
                }
        ).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mClientSocket != null) {
            try {
                mClientSocket.shutdownInput();
                mClientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void connectTCPServer() {
        Socket socket = null;
        while (socket == null){

            try {
                socket = new Socket("localhost",8688);
                mClientSocket = socket;
                mPrintWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
                mHandler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED);
                Log.d(TAG,"connect server success");
            } catch (IOException e) {
                SystemClock.sleep(1000);
                Log.d(TAG,"connect top server failed ,retry...");
                e.printStackTrace();
            }
        }

        //接受服务器端消息
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while(!TcpClientActivity.this.isFinishing()){
                String msg = br.readLine();
                Log.d(TAG,"receive:"+msg);
                if(msg != null){
                    String time =  new SimpleDateFormat("(HH:mm:ss)").format(System.currentTimeMillis());
                    final String showMsg = "server " + time + ":" +msg +"\n";
                    mHandler.obtainMessage(MESSAGE_REXEIVE_NEW_MSG,showMsg).sendToTarget();
                }
            }

            Log.d(TAG,"quit...");
            br.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        if (v == mSendButton){
            final String msg = mMessageEditText.getText().toString();
            if(!TextUtils.isEmpty(msg) && mPrintWriter != null){
                new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                mPrintWriter.println(msg);
                            }
                        }
                ).start();
                mMessageEditText.setText("");
                String time = new SimpleDateFormat("(HH:mm:ss)").format(System.currentTimeMillis());
                final String showMsg = "self " + time + ":" +msg +"\n";
                mMessageTextView.setText(mMessageTextView.getText() + showMsg);
            }
        }

    }


}
