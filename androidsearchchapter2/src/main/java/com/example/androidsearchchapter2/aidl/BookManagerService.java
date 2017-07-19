package com.example.androidsearchchapter2.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.androidsearchchapter2.aidl.Book;
import com.example.androidsearchchapter2.aidl.IBookManager;
import com.example.androidsearchchapter2.aidl.IOnNewBookArrivedListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by heyukun on 2017/7/18.
 */

public class BookManagerService extends Service {
    private static final String TAG = "BookManagerService";

    /**
     * CopyOnWriteArrayList是ArrayList 的一个线程安全的变体，
     * 其中所有可变操作（add、set等等）都是通过对底层数组进行一次新的复制来实现的。
     * 支持并发读写
     */
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();

    private AtomicBoolean mIsServiceDestoryed = new AtomicBoolean(false);

    private CopyOnWriteArrayList<IOnNewBookArrivedListener> mListenerList = new CopyOnWriteArrayList<>();

    private Binder mBinder = new IBookManager.Stub(){

        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            if(!mListenerList.contains(listener)){
                mListenerList.add(listener);
            }else {
                Log.d(TAG,"already exists");
            }
            Log.d(TAG,"registerListener , size:"+mListenerList.size());
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            if(mListenerList.contains(listener)){
                mListenerList.remove(listener);
                Log.d(TAG,"unregister listener success");
            }else {
                Log.d(TAG,"not found , can not unregister");
            }
            Log.d(TAG,"unregisterListener ,current size:"+mListenerList.size());

        }
    };


    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1,"Android"));
        mBookList.add(new Book(2,"IOS"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                //do background processing here...
                while (!mIsServiceDestoryed.get()){
                    try{
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int bookId = mBookList.size() + 1;
                    Book newBook =  new Book(bookId,"new Book#"+bookId);

                    try {
                        onNewBookArrived(newBook);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        mIsServiceDestoryed.set(true);
        super.onDestroy();
    }

    private void onNewBookArrived(Book book) throws RemoteException{
        mBookList.add(book);
        Log.d(TAG,"onNewBookArrived ,notify listeners:"+mListenerList.size());
        for (int i=0;i<mListenerList.size();i++) {
            IOnNewBookArrivedListener listener = mListenerList.get(i);
            Log.d(TAG, "onNewBookArrived ,notify listener:" + listener);
            listener.onNewBookArrived(book);
        }
    }

}
