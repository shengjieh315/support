package com.fire.support.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fire.support.R;
import com.fire.support.base.BaseActivity;
import com.fire.support.ui.demo.GaussianBlurActivity;
import com.fire.support.utils.PhoneHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tv_hello)
    TextView tvHello;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public void initListener(Bundle savedInstanceState) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @OnClick({R.id.tv_hello})
    public void onClickButterKnife(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_hello:
                startActivity(new Intent(this, GaussianBlurActivity.class));
                break;
        }
    }


}
