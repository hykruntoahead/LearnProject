package com.example.androidsearchchapter2.contentprovider;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.androidsearchchapter2.R;
import com.example.androidsearchchapter2.User;
import com.example.androidsearchchapter2.aidl.Book;

/**
 * Created by heyukun on 2017/7/19.
 */

public class ProviderActivity extends Activity {
    private static final String TAG = "ProviderActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_provider);
        //与xml中BookProvider 指定的 android:authorities 一致
        ContentValues values = new ContentValues();
        values.put("_id",6);
        values.put("name","程序设计的艺术");
        getContentResolver().insert(BookProvider.BOOK_CONTENT_URI,values);
        Cursor bookCursor = getContentResolver().query(BookProvider.BOOK_CONTENT_URI,new String[]{"_id","name"},null,null,null);
        while (bookCursor.moveToNext()){
            Book book = new Book();
            book.bookId = bookCursor.getInt(0);
            book.bookName = bookCursor.getString(1);
            Log.d(TAG,"query book:"+book.toString());
        }
        bookCursor.close();


        Cursor userCursor = getContentResolver().query(BookProvider.USER_CONTENT_URI,new String[]{"_id","name","sex"},null,null,null);
        while (userCursor.moveToNext()){
            User user = new User();
            user.userId = userCursor.getInt(0);
            user.userName = userCursor.getString(1);
            user.isMale = userCursor.getInt(2) == 1;
            Log.d(TAG,"query book:"+user.toString());
        }
        userCursor.close();
    }


}
