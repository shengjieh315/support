package com.fire.support.view.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fire.support.R;
import com.fire.support.utils.Utils;

import androidx.annotation.StringRes;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 加载更多的footer
 */
public class LoadMoreView extends SpRecyclerViewHeaderFooter {
    @BindView(R.id.pb)
    ProgressBar pb;
    @BindView(R.id.tv_loadmore)
    TextView tvLoadmore;
    @BindView(R.id.fl)
    FrameLayout fl;
    @BindView(R.id.footer_line)
    View footerLine;
    @BindView(R.id.custom_content)
    FrameLayout customContent;

    private boolean isNoMore;

    public LoadMoreView(Context context) {
        this(context, null);
    }

    public LoadMoreView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_loadmore, this);
        ButterKnife.bind(this, this);
    }


    public void setHeight(int height) {
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = height;
        setLayoutParams(params);
    }

    public TextView getTextView() {
        return tvLoadmore;
    }

    public void setNoVisibleMore(boolean isNoMore) {
        this.isNoMore = isNoMore;
        if (isNoMore) {
            tvLoadmore.setVisibility(View.GONE);
            pb.setVisibility(View.GONE);
            setLoadEnable(false);
        } else {
            tvLoadmore.setVisibility(View.GONE);
            pb.setVisibility(View.VISIBLE);
            setLoadEnable(true);
        }
    }

    public void setNoMore(boolean isNoMore) {
        this.isNoMore = isNoMore;
        if (isNoMore) {
            tvLoadmore.setVisibility(View.VISIBLE);
            pb.setVisibility(View.GONE);
            setLoadEnable(false);
        } else {
            tvLoadmore.setVisibility(View.GONE);
            pb.setVisibility(View.VISIBLE);
            setLoadEnable(true);
        }
    }


    public void setProgressColor(int color) {
        Utils.setProgressColor(pb, color);
    }

    public void hide() {
        hideFooterLine();
        fl.setVisibility(View.GONE);
    }

    public void hideInvisible() {
        hideFooterLine();
        fl.setVisibility(View.INVISIBLE);
    }

    public void showInvisible() {
        hideFooterLine();
        fl.setVisibility(View.VISIBLE);
    }

    public void show() {
        showFooterLine();
        customContent.setVisibility(View.GONE);
        fl.setVisibility(View.VISIBLE);
    }


    public boolean isNoMore() {
        return isNoMore;
    }

    public FrameLayout getFl() {
        return fl;
    }

    public View getFooterLine() {
        return footerLine;
    }

    public void showFooterLine() {
        if (footerLine != null) {
            footerLine.setVisibility(View.VISIBLE);
        }
    }

    public void hideFooterLine() {
        if (footerLine != null) {
            footerLine.setVisibility(View.GONE);
        }
    }

    public void setTvLoadmoreHide() {
        if (tvLoadmore != null) {
            tvLoadmore.setVisibility(View.INVISIBLE);
        }
    }

    public void setMessage(String message) {
        if (tvLoadmore != null) {
            tvLoadmore.setText(message);
        }
    }

    public void setMessage(@StringRes int resid) {
        if (tvLoadmore != null) {
            tvLoadmore.setText(resid);
        }
    }


    public void setCustomContentView(View customView) {
        hide();
        customContent.addView(customView);
        customContent.setVisibility(View.VISIBLE);
    }
}
