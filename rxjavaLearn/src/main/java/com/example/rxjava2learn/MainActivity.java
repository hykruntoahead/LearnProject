package com.example.rxjava2learn;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;

/**
 * 代码参考  Rxjava 一些博文
 */
public class MainActivity extends AppCompatActivity {
    public static final String TAG = "RX:MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printDebugToUseRx();
            }
        });

    }

    private void printDebugToUseRx(){
//        blog1();
        blog2();
    }


   void blog1(){
       helloWorld();
       simpleHelloWorld();
       EasyMapHelloWorld();
       AdvancedMapHelloWorld();
   }

   void blog2(){
       useFrom();
       useDefer();
//       useInterval();
       useRange();
       useTimer();
       useRepeat();
       useFlatmap();
       useFilter();
   }

    void blog3(){

    }

    void blog4(){

    }

//------------------------------blog1---------------------------------------------------------------------//
    /**
     * eg:1-1__>>hello world
     */
    void helloWorld() {
        //被观察者
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Rxjava---Hello World");
                subscriber.onCompleted();
            }
        });
        //观察者
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, s);
            }
        };
        //subscriber对observable的订阅
        observable.subscribe(subscriber);
    }

    /**
     * eg:1-2__>>simple hello world
     */
    void simpleHelloWorld() {
        Observable.just("Simple hello world").subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
             Log.d(TAG,s);
            }
        });
    }

    //ps:Subscribers更应该做的事情是“响应”，响应Observable发出的事件，而不是去修改。---这就引出了"操作符"

    /**
     * eg:1-3:操作符之 —— map() 简单使用
     */

      void EasyMapHelloWorld(){
          Observable.just("hello world").map(new Func1<String, String>() {
              @Override
              public String call(String s) {
                  return s + "   \n\r -from Tom He";
              }
          }).subscribe(new Action1<String>() {
              @Override
              public void call(String s) {
                  Log.d(TAG,s);
              }
          });
      }

    /**
     * eg:1-4：操作符 map() 进阶使用
     */
     void AdvancedMapHelloWorld(){
         Observable.just("hello world").map(new Func1<String,Integer>() {
             @Override
             public Integer call(String s) {

                 return s.hashCode();
             }
         }).map(new Func1<Integer, String>() {
             @Override
             public String call(Integer integer) {
                 return Integer.toString(integer);
             }
         }).subscribe(new Action1<String>() {
             @Override
             public void call(String s) {
                 Log.d(TAG,s);
             }
         });
     }


    /**
     * eg:1 总结
     * 1.Observable和Subscriber可以做任何事情
     Observable可以是一个数据库查询，Subscriber用来显示查询结果；
     Observable可以是屏幕上的点击事件，Subscriber用来响应点击事件；
     Observable可以是一个网络请求，Subscriber用来显示请求结果。

     2.Observable和Subscriber是独立于中间的变换过程的。
     在Observable和Subscriber中间可以增减任何数量的map。
     整个系统是高度可组合的，操作数据是一个很简单的过程。

     */
//------------------------------blog2--------------------------------------------------------------------//

    /**
     * from() 操作符 历集合，发送每个item：
     */
    String[] urls = {"http://blog.csdn.net/lzyzsd/article/details/41833541",
            "http://blog.csdn.net/lzyzsd/article/details/44094895",
            "http://blog.csdn.net/lzyzsd/article/details/44891933",
            "http://blog.csdn.net/lzyzsd/article/details/45033611"};

    void useFrom(){
        Observable.from(urls)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d(TAG,s);
                    }
                });
    }


    /**
     * defer() 操作符 使用defer( )，有观察者订阅时才创建Observable，并且为每个观察者创建一个新的Observable
     */

    void  useDefer(){
        Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                return Observable.just("deferObservable");
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d(TAG,s);
            }
        });
    }

    /**
     * 使用interval( ),创建一个按固定时间间隔发射整数序列的Observable，可用作定时器：
     */

    void useInterval(){
        //每隔一秒发送一次
        Observable.interval(1, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Log.d(TAG,"time:"+aLong);
            }
        });
    }


    /**
     * 使用range( ),创建一个发射特定整数序列的Observable，
     * 第一个参数为起始值，第二个为发送的个数，如果为0则不发送，负数则抛异常
     * java.lang.IllegalArgumentException: Count can not be negative
     */

    void useRange(){
        Observable.range(0,10).map(new Func1<Integer, String>() {
            @Override
            public String call(Integer integer) {
                return "range-"+integer;
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d(TAG,s);
            }
        });
    }

    /**
     * 使用timer( ),创建一个Observable，
     *它在一个给定的延迟后发射一个特殊的值，等同于Android中Handler的postDelay( )方法
     */

   void useTimer(){
       //3秒钟后发射
       Observable.timer(3,TimeUnit.SECONDS)
               .map(new Func1<Long, String>() {
           @Override
           public String call(Long aLong) {
               return "Timer-postDelay:"+aLong;
           }
       }).subscribe(new Action1<String>() {
           @Override
           public void call(String s) {
               Log.d(TAG,s);
           }
       });

   }

    /**
     * 使用repeat( ),创建一个重复发射特定数据的Observable:
     */
     void useRepeat(){
         //repeat 10 次
         Observable.just("just").repeat(10).subscribe(new Action1<String>() {
             @Override
             public void call(String s) {
                 Log.d(TAG,s);
             }
         });
     }


    /**
     * use flatMap()
     */
    void useFlatmap(){
        Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                List<String> strings = new ArrayList<String>() ;
                for (String url : urls){
                    strings.add(url);
                }
                subscriber.onNext(strings);
            }
        }).flatMap(new Func1<List<String>, Observable<String>>() {
            @Override
            public Observable<String> call(List<String> strings) {
                return Observable.from(strings);
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d(TAG,s);
            }
        });
    }

    /**
     * use filter() 过滤
     */

    void useFilter(){
        Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                List<String> strings = new ArrayList<String>() ;
                for (String url : urls){
                    strings.add(url);
                }
                subscriber.onNext(strings);
            }
        }).flatMap(new Func1<List<String>, Observable<String>>() {
            @Override
            public Observable<String> call(List<String> strings) {
                return Observable.from(strings);
            }
        }).filter(new Func1<String, Boolean>() {
            @Override
            public Boolean call(String s) {
                //保留 字符串中包含 "1"
                return s.contains("1");
            }
        }) .subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d(TAG+"filter",s);
            }
        });
    }



}
