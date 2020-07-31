package com.fire.support.ui.demo;

import android.os.Bundle;

import com.fire.adapter.SpHolderHelper;
import com.fire.adapter.SpRVAdapter;
import com.fire.support.R;
import com.fire.support.base.BaseActivity;
import com.fire.support.view.other.RecyclerViewEmpty;
import com.fire.support.view.refresh.LoadMoreView;
import com.fire.support.view.refresh.ProgressLoadingView;
import com.fire.support.view.refresh.RefreshLayout;
import com.fire.support.view.refresh.SpRecyclerViewHeaderFooter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PullActivity extends BaseActivity implements SpRecyclerViewHeaderFooter.OnLoadMoreListener, RefreshLayout.OnRefreshListener {

    @BindView(R.id.refresh)
    RefreshLayout mRefresh;
    @BindView(R.id.footer)
    LoadMoreView mFooter;
    @BindView(R.id.refresh_content_view)
    RecyclerViewEmpty mRecyclerView;
    @BindView(R.id.loadingView)
    ProgressLoadingView loadingView;


    private SpRVAdapter<String> adapter;

    private int pageIndex;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_pull);
        ButterKnife.bind(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mFooter.setLoadMoreListener(this);
        mRefresh.setOnRefreshListener(this);
        mRefresh.setRefreshEnabled(true);
        mFooter.attachTo(mRecyclerView, false);
        mRecyclerView.setEmptyView(loadingView);
        loadingView.setProgress(true, false, "");
        adapter = new SpRVAdapter<String>(mRecyclerView, R.layout.item_pull) {
            @Override
            protected void setView(SpHolderHelper helper, int position, String bean) {
                helper.setText(R.id.tv_title, bean);
            }

            @Override
            protected void setItemListener(SpHolderHelper helper) {

            }
        };
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void initListener(Bundle savedInstanceState) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        onRefresh();
    }

    @Override
    public void onRefresh() {
        pageIndex = 0;
        getData();
    }

    @Override
    public void onLoadMore() {
        getData();
    }

    private void getData() {
        mRefresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefresh.refreshComplete();
                mFooter.loadMoreComplete();

                //测试数据
                int size = pageIndex == 10 ? 3 : 20;
                List<String> list = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    list.add("hello = " + (pageIndex * 10 + i + 1));
                }
                if (pageIndex == 0) {
                    adapter.setList(list);
                } else {
                    adapter.addMoreList(list);
                }
                pageIndex++;
                mFooter.setNoMore(list.size() < 20);
            }
        }, 1000);
    }


}
