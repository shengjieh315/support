package com.fire.support.adapter;

import com.canyinghao.canadapter.CanHolderHelper;
import com.canyinghao.canadapter.CanRVAdapter;
import com.fire.support.R;
import com.fire.support.model.MainItemBean;

import androidx.recyclerview.widget.RecyclerView;

public class MainAdapter extends CanRVAdapter<MainItemBean> {

    public MainAdapter(RecyclerView mRecyclerView) {
        super(mRecyclerView, R.layout.item_main);
    }

    @Override
    protected void setView(CanHolderHelper helper, int position, MainItemBean bean) {
        helper.setText(R.id.tv_title, bean.title);
    }

    @Override
    protected void setItemListener(CanHolderHelper helper) {
        helper.setItemChildClickListener(R.id.rl_content);
    }
}
