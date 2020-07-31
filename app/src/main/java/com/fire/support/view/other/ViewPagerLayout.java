package com.fire.support.view.other;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import androidx.viewpager.widget.ViewPager;

/**
 * viewpager嵌套viewpager用此布局包裹子viewpager
 */
public class ViewPagerLayout extends FrameLayout {
    private ViewPager viewpager;
    private ViewPager viewpager1;
    private float startX;
    private float startY;


    public ViewPagerLayout(Context context) {
        this(context, null);
    }

    public ViewPagerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPagerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setViewpager1(ViewPager viewpager1) {
        this.viewpager1 = viewpager1;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (viewpager == null) {
            viewpager = (ViewPager) getChildAt(0);
        }

        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:// 按下
                startX = event.getX();
                startY = event.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            // 滑动，在此对里层viewpager的第一页和最后一页滑动做处理
            case MotionEvent.ACTION_MOVE:

                if (startX <= 0 || startY <= 0) {

                    break;
                }

                float eventY = event.getY();
                float eventX = event.getX();

                float offX = eventX - startX;
                float offY = eventY - startY;


                startX = eventX;
                startY = eventY;


                boolean moved = Math.abs(offX) > Math.abs(offY);

                if (!moved) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    break;
                }


                if (offX < 0) {
                    if (viewpager.getCurrentItem() == viewpager
                            .getAdapter().getCount() - 1) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
                // 里层viewpager已经是第一页，此时继续向左滑（手指从左往右滑）
                else if (offX > 0) {
                    if (viewpager.getCurrentItem() == 0) {
                        if (viewpager1 != null) {

                            if (viewpager1.getCurrentItem() == 0) {


                                getParent().requestDisallowInterceptTouchEvent(false);
                            } else {

                                getParent().requestDisallowInterceptTouchEvent(true);

                            }

                        } else {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }

                    }
                }
                break;
            case MotionEvent.ACTION_UP:// 抬起
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
        }

        boolean dispatch = false;
        try {
            dispatch = super.dispatchTouchEvent(event);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dispatch;


    }


}
