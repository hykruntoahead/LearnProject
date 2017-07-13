package com.example.android4chapter10;

import android.graphics.Canvas;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

/**
 * Created by heyukun on 2017/7/12.
 */

public class MyWallPaperService extends WallpaperService {
    private static final int FPS = 30;
    private final Handler mHandler = new Handler();
    private boolean mDrawFlag;
    @Override
    public Engine onCreateEngine() {
        return new MyWallPaperServiceEngine();
    }



    class MyWallPaperServiceEngine extends WallpaperService.Engine {
        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
        }


        @Override
        public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {
            super.onOffsetsChanged(xOffset, yOffset, xOffsetStep, yOffsetStep, xPixelOffset, yPixelOffset);
            //每当用户手指划过多个主屏幕面板时触发
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            super.onTouchEvent(event);
            //当live wallpaper收到触摸事件时触发
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            drawFrame();
            mDrawFlag = true;
        }

        private void drawFrame() {
            synchronized (this) {
                if (mDrawFlag) {

                    final SurfaceHolder holder = getSurfaceHolder();

                    Canvas canvas = null;

                    try {
                        canvas = holder.lockCanvas();
                        if (canvas != null) {
                            //在画布上绘制
                            canvas.drawARGB(255, 100, 100, 100);
                        }
                    } finally {
                        if (canvas != null) {
                            holder.unlockCanvasAndPost(canvas);
                        }
                    }
                    mHandler.removeCallbacks(drawSurface);
                    mHandler.postDelayed(drawSurface, 1000 / FPS);
                }
            }
        }

        private final Runnable drawSurface = new Runnable() {
            @Override
            public void run() {
                drawFrame();
            }
        };


        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            synchronized (this) {
                mDrawFlag = false;
            }
        }
    }

}
