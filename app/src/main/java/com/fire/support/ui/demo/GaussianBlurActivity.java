package com.fire.support.ui.demo;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.fire.support.R;
import com.fire.support.base.BaseActivity;
import com.fire.support.view.BlurringView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 高斯模糊
 */
public class GaussianBlurActivity extends BaseActivity {

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
    }

    @Override
    public void initListener(Bundle savedInstanceState) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }
}
