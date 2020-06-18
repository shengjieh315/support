package com.fire.support.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.canyinghao.canadapter.CanOnItemListener;
import com.fire.support.R;
import com.fire.support.adapter.MainAdapter;
import com.fire.support.base.BaseActivity;
import com.fire.support.model.MainItemBean;
import com.fire.support.ui.demo.GaussianBlurActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.recycler)
    RecyclerView recycler;

    private MainAdapter mMainAdapter;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mMainAdapter = new MainAdapter(recycler);
        recycler.setLayoutManager(new LinearLayoutManager(context));
        recycler.setAdapter(mMainAdapter);

    }

    @Override
    public void initListener(Bundle savedInstanceState) {
        mMainAdapter.setOnItemListener(new CanOnItemListener() {
            @Override
            public void onItemChildClick(View view, int position) {
                switch (mMainAdapter.getItem(position).id) {
                    case 1://高斯模糊
                        startActivity(new Intent(MainActivity.this, GaussianBlurActivity.class));
                        break;
                }
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        List<MainItemBean> list = new ArrayList<>();

        MainItemBean gaussian = new MainItemBean();
        gaussian.id = 1;
        gaussian.title = "高斯模糊";

        list.add(gaussian);

        mMainAdapter.setList(list);

    }

}
