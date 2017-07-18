package com.example.androidsearchchapter2;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by heyukun on 2017/7/18.
 */

/**
 * messenger 信使 底部为aidl实现
 */

public class MessengerActivity extends Activity {
    private static final String TAG = "MessengerActivity";

    private Messenger mService;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);
            Message msg = Message.obtain(null,Constants.MSG_FROM_CLIENT);
            Bundle data = new Bundle();
            data.putString("msg","hello,this is client");
            msg.setData(data);
            //指定 replyTo Messenger
            msg.replyTo = mGetReplyMessenger;
            try {
                mService.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private static class MessengerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MSG_FROM_SERVICE:
                    Log.i(TAG,"receive msg from Service:"+msg.getData().getString("reply"));
                    break;
                default:
                super.handleMessage(msg);
            }
        }
    }


    private Messenger mGetReplyMessenger = new Messenger(new MessengerHandler());


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        Intent intent = new Intent(this,MessengerService.class);
        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }
}
