package com.fire.support.adapter;

import android.view.View;

import com.canyinghao.canadapter.CanHolderHelper;
import com.canyinghao.canadapter.CanRVAdapter;
import com.fire.support.R;
import com.fire.support.utils.PhoneHelper;
import com.fire.support.utils.Utils;
import com.fire.support.view.layoutmanager.StackLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

public class GalleryAdapter extends CanRVAdapter<Integer> {

    public GalleryAdapter(RecyclerView mRecyclerView) {
        super(mRecyclerView, R.layout.item_gallery);
    }

    @Override
    protected void setView(CanHolderHelper helper, int position, Integer bean) {
        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.noMultiClick(view);
                RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
                if (layoutManager instanceof StackLayoutManager) {
                    StackLayoutManager managerUp = ((StackLayoutManager) layoutManager);
                    if (managerUp.isClickCenter(position)) {
                        PhoneHelper.getInstance().show("click");
                    } else {
                        ((StackLayoutManager) layoutManager).smoothScrollToPosition(position);
                    }
                }
            }
        });
    }

    @Override
    protected void setItemListener(CanHolderHelper helper) {

    }

    @Override
    public int getItemCount() {
        return 2000;
    }
}
