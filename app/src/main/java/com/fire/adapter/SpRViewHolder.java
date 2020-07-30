package com.fire.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

public class SpRViewHolder extends RecyclerView.ViewHolder {

    protected Context mContext;
    protected SpHolderHelper mHolderHelper;
    protected RecyclerView mRecyclerView;
    protected SpOnItemListener mOnItemListener;

    protected int viewType;

    public SpRViewHolder(RecyclerView recyclerView, View itemView) {
        this(recyclerView, itemView, 0);
    }


    public SpRViewHolder(RecyclerView recyclerView, View itemView, int ratio) {
        super(itemView);

        if (ratio > 0) {

            ViewGroup.LayoutParams lp = itemView.getLayoutParams() == null ? new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT) : itemView.getLayoutParams();
            if (recyclerView.getLayoutManager().canScrollHorizontally()) {
                lp.width = (recyclerView.getWidth() / ratio - recyclerView.getPaddingLeft() - recyclerView.getPaddingRight());
            } else {
                lp.height = recyclerView.getHeight() / ratio - recyclerView.getPaddingTop() - recyclerView.getPaddingBottom();
            }

            itemView.setLayoutParams(lp);
        }


        this.mRecyclerView = recyclerView;
        this.mContext = mRecyclerView.getContext();
        this.mHolderHelper = SpHolderHelper.holderHelperByRecyclerView(itemView);


    }


    public SpRViewHolder setViewType(int viewType) {
        this.viewType = viewType;
        return this;
    }

    public int getViewType() {
        return viewType;
    }

    public SpHolderHelper getSpHolderHelper() {
        return mHolderHelper;
    }

    public SpHolderHelper getCanHolderHelper() {
        return mHolderHelper;
    }

    public void setOnItemListener(SpOnItemListener onItemListener) {
        this.mOnItemListener = onItemListener;
    }


}
