package com.fire.support.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.ScaleAnimation;

import com.canyinghao.canadapter.CanOnItemListener;
import com.fire.support.R;
import com.fire.support.adapter.MainAdapter;
import com.fire.support.base.BaseActivity;
import com.fire.support.model.MainItemBean;
import com.fire.support.ui.demo.GalleryActivity;
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
                    case 2://循环画廊
                        startActivity(new Intent(MainActivity.this, GalleryActivity.class));
                        break;
                    case 3://动画
                        startAnimation(view.findViewById(R.id.view_progress));
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
        MainItemBean gallery = new MainItemBean();
        gallery.id = 2;
        gallery.title = "循环画廊";
        list.add(gallery);
        MainItemBean anim = new MainItemBean();
        anim.id = 3;
        anim.title = "动画";
        list.add(anim);
        mMainAdapter.setList(list);
    }

    private void startAnimation(View view) {
        if (view.getVisibility() == View.VISIBLE) return;
        view.setVisibility(View.VISIBLE);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 1, 1);
        scaleAnimation.setDuration(1000);
        scaleAnimation.setFillAfter(true);
        view.startAnimation(scaleAnimation);
    }

}
