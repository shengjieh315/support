package com.fire.support.ui.demo;

import android.os.Bundle;
import android.widget.ImageView;

import com.fire.support.R;
import com.fire.support.base.BaseActivity;
import com.fire.support.view.BlurringView;
import com.fire.support.view.refresh.ProgressRefreshView;
import com.fire.support.view.refresh.RefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 高斯模糊
 */
public class GaussianBlurActivity extends BaseActivity implements RefreshLayout.OnRefreshListener {

    @BindView(R.id.refresh)
    RefreshLayout mRefresh;
    @BindView(R.id.refresh_header)
    ProgressRefreshView mRefreshHeader;

    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.bv_blur)
    BlurringView bvBlur;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_gaussian_blur);
        ButterKnife.bind(this);

        bvBlur.setBlurredView(ivBg);
        bvBlur.setCanceLartifactsAtTheEdge(true);
        bvBlur.invalidate();
        mRefresh.setOnRefreshListener(this);
        mRefresh.setRefreshEnabled(true);
        mRefreshHeader.setTimeId("GaussianBlurActivity");

    }

    @Override
    public void initListener(Bundle savedInstanceState) {
        mRefresh.setOnStartUpListener(new RefreshLayout.OnStartUpListener() {
            @Override
            public void onUp() {

            }

            @Override
            public void onReset() {
                if (mRefreshHeader != null) {
                    mRefreshHeader.reSetRefreshTime();
                }
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void onRefresh() {
        bvBlur.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefresh.refreshComplete();
                mRefreshHeader.putRefreshTime();
            }
        },1000);
    }
}
