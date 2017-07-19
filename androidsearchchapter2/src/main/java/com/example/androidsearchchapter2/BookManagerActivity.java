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
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.androidsearchchapter2.aidl.Book;
import com.example.androidsearchchapter2.aidl.BookManagerService;
import com.example.androidsearchchapter2.aidl.IBookManager;
import com.example.androidsearchchapter2.aidl.IOnNewBookArrivedListener;

import java.util.List;

/**
 * Created by heyukun on 2017/7/18.
 */

public class BookManagerActivity extends Activity {
    private static final String TAG = "BookManagerActivity";
    private static final int MESSAGE_NEW_BOOK_ARRIVED = 1;

    private IBookManager mRemoteBookManager;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_NEW_BOOK_ARRIVED:
                    Log.d(TAG, "receive new book :" + msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };


    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IBookManager bookManager = IBookManager.Stub.asInterface(service);

            try {
                mRemoteBookManager = bookManager;

                List<Book> list = bookManager.getBookList();
                Log.i(TAG,"query book list ,list type:"+list.getClass().getCanonicalName());

                Book newBook = new Book(3,"Android进阶");
                bookManager.addBook(newBook);
                Log.i(TAG,"add book:"+newBook);

                List<Book> newList = bookManager.getBookList();
                Log.i(TAG,"query book list:"+newList.toString());

                mRemoteBookManager.registerListener(mOnNewBookArrivedListener);

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
          mRemoteBookManager = null;
            Log.e(TAG,"binder died");
        }
    };


    private IOnNewBookArrivedListener mOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub(){

        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            mHandler.obtainMessage(MESSAGE_NEW_BOOK_ARRIVED,newBook).sendToTarget();
        }
    };



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manager);
        Intent intent = new Intent(this,BookManagerService.class);
        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);
    }


    @Override
    protected void onDestroy() {
        if(mRemoteBookManager != null && mRemoteBookManager.asBinder().isBinderAlive()) {

            try {
                Log.i(TAG,"unregister listener:"+mOnNewBookArrivedListener);
                mRemoteBookManager.unregisterListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
        unbindService(mConnection);
        super.onDestroy();
    }
}
