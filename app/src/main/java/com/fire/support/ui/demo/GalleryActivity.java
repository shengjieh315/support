package com.fire.support.ui.demo;

import android.os.Bundle;

import com.fire.support.R;
import com.fire.support.adapter.GalleryAdapter;
import com.fire.support.base.BaseActivity;
import com.fire.support.view.layoutmanager.StackLayoutManager;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryActivity extends BaseActivity {

    @BindView(R.id.recycler)
    RecyclerView recycler;

    private GalleryAdapter mGalleryAdapter;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);

        mGalleryAdapter = new GalleryAdapter(recycler);
        StackLayoutManager stackLayoutManager = new StackLayoutManager(this, 1000);
        recycler.setLayoutManager(stackLayoutManager);
        recycler.setAdapter(mGalleryAdapter);
    }

    @Override
    public void initListener(Bundle savedInstanceState) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(0);
        list.add(0);
        list.add(0);
        list.add(0);
        list.add(0);
        mGalleryAdapter.setList(list);
    }

}
