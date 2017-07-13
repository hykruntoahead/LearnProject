package com.example.android4chapter10;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by heyukun on 2017/7/12.
 */

/**
 * live wallpaper android2.1 引入 用于创建动态的，交互式的主屏幕背景
 *
 * 为创建一个新的live wallpaper ，需要下面三个组件：
 * 1） 一个描述了与live wallpaper 关联的元数据的xml资源 这些元数据包括其作者 描述 和用来在live wallpaper选取器中代表该live wallpaper的缩略图
 * 2） 一个wallpaper service实现，他将封装、实例化和管理wallpaper引擎
 * 3） 一个wallpaper service 引擎实现（通过wallpaper service 返回），它定义了live wallpaper的UI和交互行为。live wallpaper实现的大部分内容都包含在
 *     wallpaper service 引擎中
 */

public class LiveWallpaperActivity extends AppCompatActivity {

}
