package com.example.android4chapter10;

import android.app.Application;
import android.content.res.Configuration;

/**
 * Created by heyukun on 2017/7/11.
 */

public class BaseApp extends Application {
    private static BaseApp singleton;

    public static BaseApp getInstance(){
        return singleton;
    }

    /**
     * 在创建应用程序时调用。可以重写这个方法来实例化应用程序单态，以及创建和实例化任何应用程序状态或共享资源
     */

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
    }

    /**
     * 系统资源匮乏时调用
     * 可重写这个处理程序来清空缓存或者释放不必要资源
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    /**
     *
     * @param level
     * 当运行时决定当前应用程序应该尝试减少其内存开销时（通常在它进入后台时）调用
     */
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    /**
     *
     * @param newConfig
     * 如果应用程序使用的值依赖于特定的配置，则重写这个方法来重新加载这些值，或者在应用程序级别处理配置改变
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
