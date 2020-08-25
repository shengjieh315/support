package com.fire.support.view.other;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.fire.support.R;
import com.socks.library.KLog;

import androidx.annotation.Nullable;

public class IFrameLayout extends FrameLayout {

    protected View mHeaderView;
    protected View mContentView;
    protected View mFooterView;

    public IFrameLayout(Context context) {
        super(context);
    }

    public IFrameLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public IFrameLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        KLog.e("onFinishInflate");
        final int childCount = getChildCount();
        if (childCount > 0) {
            mHeaderView = findViewById(R.id.ll_header);
            mContentView = findViewById(R.id.ll_content);
            mFooterView = findViewById(R.id.ll_footer);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        KLog.e("onMeasure");
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        measureChildWithMargins(mHeaderView, widthMeasureSpec, 0, heightMeasureSpec, 0);
        measureChildWithMargins(mContentView, widthMeasureSpec, 0, heightMeasureSpec, 0);
        measureChildWithMargins(mFooterView, widthMeasureSpec, 0, heightMeasureSpec, 0);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        KLog.e("onSizeChanged");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        KLog.e("onLayout");
        layoutHeader();
    }

    int mHeadOffY = 500;


    private void layoutHeader() {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();

        if (mHeaderView != null) {
            MarginLayoutParams lp = (MarginLayoutParams) mHeaderView.getLayoutParams();
            final int left = paddingLeft + lp.leftMargin;
            final int top = paddingTop + lp.topMargin + mHeadOffY;
            final int right = left + mHeaderView.getMeasuredWidth();
            final int bottom = top + mHeaderView.getMeasuredHeight();

            mHeaderView.layout(left, top, right, bottom);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        KLog.e("onDraw");
    }

}
