package com.fire.support.adapter;

import com.fire.adapter.SpHolderHelper;
import com.fire.adapter.SpRVAdapter;
import com.fire.support.R;
import com.fire.support.model.MainItemBean;

import androidx.recyclerview.widget.RecyclerView;

public class MainAdapter extends SpRVAdapter<MainItemBean> {

    public MainAdapter(RecyclerView mRecyclerView) {
        super(mRecyclerView, R.layout.item_main);
    }

    @Override
    protected void setView(SpHolderHelper helper, int position, MainItemBean bean) {
        helper.setText(R.id.tv_title, bean.title);
    }

    @Override
    protected void setItemListener(SpHolderHelper helper) {
        helper.setItemChildClickListener(R.id.rl_content);
    }
}
