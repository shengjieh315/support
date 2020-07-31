package com.fire.support.view.refresh;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.fire.support.R;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 自动加载的view
 */
public class ProgressLoadingView extends FrameLayout {


    @BindView(R.id.tv_loading)
    TextView tvLoading;
    @BindView(R.id.iv_progress)
    SimpleDraweeView ivProgress;

    @BindView(R.id.btn_try_again)
    TextView btnTryAgain;

    @BindView(R.id.iv_loading)
    SimpleDraweeView ivLoading;
    @BindView(R.id.fl_progress)
    ViewGroup flProgress;

    private boolean isProgress;
    private String mDifinitionMsg;

    private SpannableStringBuilder mDifinitionSpanMsg;

    private AnimationDrawable animationDrawable;

    public ProgressLoadingView(Context context) {
        this(context, null);
    }

    public ProgressLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public ProgressLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private void initView() {
        Drawable drawable = getResources().getDrawable(R.drawable.anime_refresh_header);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_progress_loading, null);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        addView(view, layoutParams);
        ButterKnife.bind(this, this);
        try {
            ivLoading.getHierarchy().setPlaceholderImage(drawable, ScalingUtils.ScaleType.CENTER_INSIDE);
            if (drawable instanceof AnimationDrawable) {
                animationDrawable = (AnimationDrawable) drawable;
                animationDrawable.start();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void startAnimation() {
        if (animationDrawable != null) {
            animationDrawable.start();
        }
    }

    public void stopAnimation() {
        if (animationDrawable != null) {
            animationDrawable.stop();
        }
    }

    public boolean isStop() {
        if (animationDrawable != null && animationDrawable.isRunning()) {
            return false;
        }
        return true;
    }

    public void setProgressTemporaryHide() {
        flProgress.setVisibility(View.GONE);
        ivLoading.setVisibility(View.INVISIBLE);
        btnTryAgain.setVisibility(View.GONE);
    }

    public void setProgress(boolean isProgress, boolean isError, @StringRes int strId) {
        setProgress(isProgress, isError, getResources().getString(strId));
    }

    public void setProgress(boolean isProgress, boolean isError, CharSequence message) {
        this.isProgress = isProgress;
        if (isProgress) {
            if (isStop()) {
                startAnimation();
            }
            flProgress.setVisibility(View.GONE);
            ivLoading.setVisibility(View.VISIBLE);
            btnTryAgain.setVisibility(View.GONE);
        } else {
            stopAnimation();
            flProgress.setVisibility(View.VISIBLE);
            ivLoading.setVisibility(View.GONE);

            if (isError) {
                btnTryAgain.setVisibility(View.VISIBLE);
            } else {
                btnTryAgain.setVisibility(View.GONE);
            }
        }

        if (TextUtils.isEmpty(message)) {
            if (isProgress) {
                message = getResources().getString(R.string.msg_trying_loading);
            } else {
                if (isError) {
                    message = getResources().getString(R.string.msg_network_error);
                } else {
                    if (null == mDifinitionMsg) {
                        message = getResources().getString(R.string.msg_no_data_available);
                    } else {
                        message = mDifinitionMsg;
                    }
                }
            }
        }

        if (mDifinitionSpanMsg != null && !isProgress && !isError) {
            tvLoading.setSingleLine(false);
            tvLoading.setGravity(Gravity.CENTER);
            tvLoading.setLineSpacing(5.0f, 1.0f);
            tvLoading.setText(mDifinitionSpanMsg);
        } else {
            tvLoading.setText(message);
        }
    }

    public void setEmptyPic(@DrawableRes int res) {
        ivProgress.setActualImageResource(res);
    }

    /**
     * 自定义empty页面message
     */
    public void setMessage(String message) {
        mDifinitionMsg = message;
    }

    /**
     * 自定义empty页面message
     */
    public void setMessage(SpannableStringBuilder message) {
        mDifinitionSpanMsg = message;
    }

    public void setOnTryAgainOnClickListener(View.OnClickListener clickListener) {
        btnTryAgain.setOnClickListener(clickListener);
    }

    public boolean isProgress() {
        return isProgress;
    }

    public boolean isError() {
        return btnTryAgain.getVisibility() == View.VISIBLE;
    }

    public TextView getTvLoading() {
        return tvLoading;
    }

    public TextView getBtnTryAgain() {
        return btnTryAgain;
    }

    public ImageView getIvProgress() {
        return ivProgress;
    }

    public SimpleDraweeView getSDVProgress() {
        return ivProgress;
    }


    public ImageView getProgressImage() {
        return ivProgress;
    }


    public void setEmptyDataClickListener(View.OnClickListener onClickListener) {
        flProgress.setOnClickListener(onClickListener);
    }

}
