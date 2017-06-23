package com.example.viewpagerindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by heyukun on 2017/6/15.
 */

public class ViewPagerIndicator extends LinearLayout {
    private Paint mPaint;
    private Path mPath;
    private ViewPager mViewPager;
    private OnViewPagerChangeListener mViewPagerChangeListener;
    private static final float WIDTH_PERCENT = 1 / 6f;
    private static final int TITLE_COLOR = 0x66ffffff;
    private static final int TITLE_COLOR_HIGH = 0xffffffff;
    private int mTriAngleWidth;
    private int mTriAngleHeight;

    private int mInitTranslateX;
    private int mTranslateX;

    private int DEFAULT_VISIBLE_TAB_COUNT = 3;

    private int mTableVisibleCount;

    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.ViewPagerIndicator);

        mTableVisibleCount = a.getInt(R.styleable.ViewPagerIndicator_visible_tab_count, DEFAULT_VISIBLE_TAB_COUNT);

        if (mTableVisibleCount <= 0) {
            mTableVisibleCount = DEFAULT_VISIBLE_TAB_COUNT;
        }

        mPaint = new Paint();
        //设置画笔颜色
        mPaint.setColor(Color.parseColor("#ffffff"));
        //抗锯齿
        mPaint.setAntiAlias(true);
        //填充
        mPaint.setStyle(Paint.Style.FILL);
        //避免绘制path太尖锐
        mPaint.setPathEffect(new CornerPathEffect(3));

        a.recycle();
    }

    public ViewPagerIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int count = getChildCount();
        if (count == 0) return;
        Log.i("ItemViewClick:","count:"+count);
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
//            获取子view  LayoutParams
            LinearLayout.LayoutParams lp = (LayoutParams) view.getLayoutParams();
            lp.weight = 0;
            lp.width = getScreenWidth() / mTableVisibleCount;
            view.setLayoutParams(lp);
        }

        setItemClickEvent();
    }

//    //显示子view layout
//    @Override
//    protected void onFinishInflate() {
//        super.onFinishInflate();
//
//    }


    private void setItemClickEvent(){
        int cCount = getChildCount();
//        Log.i("ItemViewClick:","count:"+cCount);
        for (int i =0;i<cCount;i++) {
            final int j = i;
            TextView view = (TextView)getChildAt(i);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mViewPager != null) {
                        mViewPager.setCurrentItem(j);
                    }
                }
            });
        }
    }


    //获取屏幕宽度
    private int getScreenWidth() {

        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics disMes = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(disMes);
        return disMes.widthPixels;
    }

    //绘制三角形下标 绘制VIew本身的内容，通过调用View.onDraw(canvas)函数实现

    //  绘制自己的孩子通过dispatchDraw（canvas）实现
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.save();
        //移动
        canvas.translate(mInitTranslateX + mTranslateX,getHeight()+5);
        //绘制path
        canvas.drawPath(mPath,mPaint);
        canvas.restore();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mTriAngleWidth = (int) (w / mTableVisibleCount * WIDTH_PERCENT);

        mInitTranslateX = w / mTableVisibleCount / 2 - mTriAngleWidth / 2;

        initTriAngle();

    }

    //初始化三角形
    private void initTriAngle() {

        mTriAngleHeight = mTriAngleWidth / 2;
        mPath = new Path();
        mPath.moveTo(0, 0);
        //到底点
        mPath.lineTo(mTriAngleWidth, 0);
        //到顶点
        mPath.lineTo(mTriAngleWidth / 2, -mTriAngleHeight);
        //封闭
        mPath.close();

    }

    /**
     * @param titles     显示 tab 标题集合
     * @param visibleTab 可见tab数
     */
    public void setTitlesAndVisibleCount(List<String> titles, int visibleTab) {
        if (visibleTab < 3) {
            mTableVisibleCount = 3;
        } else if (visibleTab > titles.size()) {
            mTableVisibleCount = titles.size();
        } else {
            mTableVisibleCount = visibleTab;
        }

        this.removeAllViews();
        for (String title : titles) {
            addView(generateTextView(title));
        }
    }

    public void setViewPager(ViewPager mViewPager, int currentPage) {
        this.mViewPager = mViewPager;
        this.mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                scroll(position, positionOffset);
                if (mViewPagerChangeListener != null) {
                  mViewPagerChangeListener.onPageScrolled(position,positionOffset,positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
                setHighTextColor(position);
                if (mViewPagerChangeListener != null) {
                    mViewPagerChangeListener.onPageSelected(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(mViewPagerChangeListener != null){
                    mViewPagerChangeListener.onPageScrollStateChanged(state);
                }

            }
        });
        this.mViewPager.setCurrentItem(currentPage);
        setHighTextColor(currentPage);
    }


    private View generateTextView(String title) {
        TextView textView = new TextView(getContext());
        LinearLayout.LayoutParams lp = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.width = getScreenWidth() / mTableVisibleCount;
        textView.setText(title);
        textView.setTextColor(TITLE_COLOR);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        textView.setLayoutParams(lp);
        return textView;
    }

    public void scroll(int position, float positionOffset) {
        int tabWidth = getWidth() / mTableVisibleCount;
        mTranslateX = (int) ((position + positionOffset) * tabWidth);
        invalidate();

        if (mTableVisibleCount != 1) {
            if (position >= mTableVisibleCount - 2 && positionOffset > 0 && getChildCount() > mTableVisibleCount && position < getChildCount() - 2) {
                this.scrollTo((int) ((position - mTableVisibleCount + 2) * tabWidth + positionOffset * tabWidth), 0);
            }
        } else {
            this.scrollTo((int) (position + tabWidth + positionOffset * positionOffset * tabWidth), 0);
        }

    }

    public void addListener(OnViewPagerChangeListener mViewPagerChangeListener) {
        this.mViewPagerChangeListener = mViewPagerChangeListener;
    }


    public interface OnViewPagerChangeListener {

        void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

        void onPageSelected(int position);

        void onPageScrollStateChanged(int state);
    }
    private void resetTextColor(){
        for (int i=0;i<getChildCount();i++){
            View view = getChildAt(i);
            if(view instanceof TextView){
                ((TextView) view).setTextColor(TITLE_COLOR);
            }
        }
    }


    private void setHighTextColor(int pos){
        resetTextColor();
        View view = getChildAt(pos);
        if(view instanceof TextView){
            ((TextView) view).setTextColor(TITLE_COLOR_HIGH);
        }
    }

}
