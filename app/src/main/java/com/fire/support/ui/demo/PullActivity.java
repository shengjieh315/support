package com.fire.support.ui.demo;

import android.os.Bundle;

import com.fire.support.R;
import com.fire.support.base.BaseActivity;

import butterknife.ButterKnife;

public class PullActivity extends BaseActivity {

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_pull);
        ButterKnife.bind(this);
    }

    @Override
    public void initListener(Bundle savedInstanceState) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }
}
