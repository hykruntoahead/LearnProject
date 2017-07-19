// IOnNewBookArrivedListener.aidl
package com.example.androidsearchchapter2.aidl;
import com.example.androidsearchchapter2.aidl.Book;

// Declare any non-default types here with import statements
//aidl接口 以通知客户端（观察者模式）
interface IOnNewBookArrivedListener {
   void onNewBookArrived(in Book newBook);
}
