package com.fire.support.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.ScaleAnimation;

import com.fire.adapter.SpOnItemListener;
import com.fire.support.R;
import com.fire.support.adapter.MainAdapter;
import com.fire.support.base.BaseActivity;
import com.fire.support.model.MainItemBean;
import com.fire.support.ui.demo.GalleryActivity;
import com.fire.support.ui.demo.GaussianBlurActivity;
import com.fire.support.ui.demo.PullActivity;
import com.fire.support.ui.demo.RxJavaActivity;

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
        mMainAdapter.setOnItemListener(new SpOnItemListener() {
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
                    case 4://rx
                        startActivity(new Intent(MainActivity.this, RxJavaActivity.class));
                        break;
                    case 5://下拉刷新、加载更多
                        startActivity(new Intent(MainActivity.this, PullActivity.class));
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
        MainItemBean rx = new MainItemBean();
        rx.id = 4;
        rx.title = "rx";
        list.add(rx);
        MainItemBean pull = new MainItemBean();
        pull.id = 5;
        pull.title = "下拉刷新、加载更多";
        list.add(pull);
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

    @Override
    protected boolean isPaddingStatusBar() {
        return true;
    }

    @Override
    protected int getBackgroundResource() {
        return R.color.colorWhite;
    }
}
