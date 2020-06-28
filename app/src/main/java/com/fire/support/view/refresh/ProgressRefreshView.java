package com.fire.support.view.refresh;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.fire.support.R;
import com.fire.support.helper.DateHelper;
import com.fire.support.utils.PreferenceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 刷新头
 */
public class ProgressRefreshView extends FrameLayout implements Refresh {

    @BindView(R.id.tv_refresh)
    TextView tvRefresh;
    @BindView(R.id.iv_refresh)
    SimpleDraweeView ivRefresh;
    @BindView(R.id.iv_loading)
    SimpleDraweeView ivLoading;
    @BindView(R.id.view_space)
    View viewSpace;

    private String lastTime;

    private AnimationDrawable animationDrawable;

    public static final String REFRESH_TIME = "REFRESH_TIME_";

    private String timeId;


    public ProgressRefreshView(Context context) {
        this(context, null);
    }

    public ProgressRefreshView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(getContext()).inflate(R.layout.view_progress_refresh, this);
        ButterKnife.bind(this, this);
    }

    public void setTimeId(String timeId) {
        this.timeId = timeId;
    }

    public void setTopShow(int h) {
        ViewGroup.LayoutParams params = viewSpace.getLayoutParams();
        params.height = h;
        viewSpace.setLayoutParams(params);
        viewSpace.setVisibility(View.VISIBLE);
    }

    @Override
    public void onReset() {
        ivRefresh.setVisibility(View.GONE);
        ivLoading.setVisibility(View.GONE);
        lastTime = "";
    }

    @Override
    public void onPrepare() {


    }


    @Override
    public void onRelease() {
        ivRefresh.setVisibility(View.GONE);
        Drawable ivDrawable = getResources().getDrawable(R.mipmap.icon_all_loading_1);
        ivRefresh.getHierarchy().setPlaceholderImage(ivDrawable, ScalingUtils.ScaleType.CENTER_INSIDE);

        ivLoading.setVisibility(View.VISIBLE);
        try {
            if (animationDrawable == null) {
                Drawable drawable = getResources().getDrawable(R.drawable.anime_refresh_header);
                ivLoading.getHierarchy().setPlaceholderImage(drawable, ScalingUtils.ScaleType.CENTER_INSIDE);
                if (drawable instanceof AnimationDrawable) {
                    animationDrawable = (AnimationDrawable) drawable;
                    animationDrawable.start();
                }
            } else {
                animationDrawable.start();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        tvRefresh.setText(R.string.refreshing);
    }

    @Override
    public void onReleaseNoEnough(float currentPercent) {
        ivRefresh.setVisibility(View.GONE);
        ivLoading.setVisibility(View.GONE);
    }

    public void setTvRefreshColor(int color) {
        tvRefresh.setTextColor(color);
    }

    @Override
    public void onComplete() {
        ivRefresh.setVisibility(View.GONE);

        ivLoading.setVisibility(View.VISIBLE);

        if (animationDrawable != null && animationDrawable.isRunning()) {
            animationDrawable.stop();
        }

        tvRefresh.setText(R.string.refresh_complete);
        lastTime = "";

    }

    @Override
    public void onPositionChange(float currentPercent) {
        if (currentPercent == 0) {
            lastTime = "";
        } else if (currentPercent > 1) {
            ivRefresh.setActualImageResource(R.mipmap.icon_all_loading_2);
            ivRefresh.setVisibility(View.VISIBLE);
            ivLoading.setVisibility(View.GONE);
            tvRefresh.setText(R.string.refresh_release);
        } else {
            ivRefresh.setActualImageResource(R.mipmap.icon_all_loading_1);
            ivRefresh.setVisibility(View.VISIBLE);
            ivLoading.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(timeId) && TextUtils.isEmpty(lastTime)) {
                long time = PreferenceUtil.getLong(REFRESH_TIME + timeId, 0, getContext());
                if (time > 0) {
                    lastTime = DateHelper.getInstance().getRecentTime(time);
                }
            }
            if (TextUtils.isEmpty(lastTime)) {
                tvRefresh.setText(R.string.refresh_pull_down);
            } else {
                tvRefresh.setText(getResources().getString(R.string.refresh_time, lastTime));
            }
        }
    }

    @Override
    public void setIsHeaderOrFooter(boolean isHeader) {

    }

    public void putRefreshTime() {
        if (!TextUtils.isEmpty(timeId)) {
            PreferenceUtil.putLong(REFRESH_TIME + timeId, System.currentTimeMillis(), getContext());
        }
    }

    public void reSetRefreshTime() {
        lastTime = "";
    }

}
