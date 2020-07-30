package com.fire.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public abstract class SpRVHFAdapter<C, G, H, F> extends RecyclerView.Adapter<SpRViewHolder> implements SpSpanSize {

    public static final int TYPE_GROUP = 0;
    public static final int TYPE_CHILD = 1;
    public static final int TYPE_HEADER = 2;
    public static final int TYPE_FOOTER = 3;

    protected int itemGroupLayoutId;
    protected int itemChildLayoutId;
    protected int itemHeaderLayoutId;
    protected int itemFooterLayoutId;

    protected Context mContext;

    protected List<G> mGroupList;
    protected List<List<C>> mChildList;

    protected SparseArray<ErvType> ervTypes;

    protected F footer;
    protected H header;
    protected RecyclerView mRecyclerView;

    public SpRVHFAdapter(RecyclerView mRecyclerView) {
        super();
        this.mContext = mRecyclerView.getContext();
        this.mRecyclerView = mRecyclerView;
        this.mGroupList = new ArrayList<>();
        this.mChildList = new ArrayList<>();
        this.ervTypes = new SparseArray<>();

    }


    public SpRVHFAdapter(RecyclerView mRecyclerView, int itemChildLayoutId, int itemGroupLayoutId, int itemHeaderLayoutId, int itemFooterLayoutId) {
        this(mRecyclerView);
        this.itemChildLayoutId = itemChildLayoutId;
        this.itemGroupLayoutId = itemGroupLayoutId;
        this.itemHeaderLayoutId = itemHeaderLayoutId;
        this.itemFooterLayoutId = itemFooterLayoutId;

    }


    public void setFooter(F footer) {
        this.footer = footer;

    }

    public F getFooter() {
        return footer;
    }

    public void setHeader(H header) {
        this.header = header;

    }

    public H getHeader() {
        return header;
    }

    public void resetStatus() {

        ervTypes.clear();

    }


    /**
     * child的实际个数
     *
     * @param group
     * @return
     */
    public int getChildItemCount(int group) {

        if (mChildList.size() <= group) {
            return 0;
        }
        return mChildList.get(group).size();
    }


    /**
     * group的个数
     *
     * @return
     */
    public int getGroupItemCount() {
        if (mGroupList.isEmpty()) {
            return 0;
        }

        return mGroupList.size();


    }


    @Override
    public int getItemCount() {

        int count = 0;

        int headerCount = header == null ? 0 : 1;
        int footerCount = footer == null ? 0 : 1;


        int groupCount = getGroupItemCount();


        if (groupCount == 0) {


            count = headerCount + footerCount + getChildItemCount(0);


        } else {

            for (int i = 0; i < groupCount; i++) {


                count += getChildItemCount(i);


            }

            count += groupCount + headerCount + footerCount;


        }


        return count;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    public G getGroupItem(int position) {
        return mGroupList.get(position);
    }

    public C getChildItem(int group, int position) {
        return mChildList.get(group).get(position);
    }


    public List<G> getGroupList() {
        return mGroupList;
    }


    public List<List<C>> getChildList() {
        return mChildList;
    }


    /**
     * 设置数据
     *
     * @param datas
     */
    public void setList(List<G> datas, List<List<C>> childData) {

        mGroupList.clear();
        mChildList.clear();

        if (datas != null && !datas.isEmpty()) {
            mGroupList.addAll(datas);
        }
        if (childData != null && !childData.isEmpty()) {
            mChildList.addAll(childData);
        }
        notifyDataSetChanged();
    }


    protected SpRViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(mContext).inflate(itemGroupLayoutId, parent, false);

        return new SpRViewHolder(mRecyclerView, itemView).setViewType(viewType);

    }

    protected SpRViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(mContext).inflate(itemChildLayoutId, parent, false);

        return new SpRViewHolder(mRecyclerView, itemView).setViewType(viewType);

    }


    protected SpRViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(mContext).inflate(itemHeaderLayoutId, parent, false);

        return new SpRViewHolder(mRecyclerView, itemView).setViewType(viewType);

    }

    protected SpRViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(mContext).inflate(itemFooterLayoutId, parent, false);

        return new SpRViewHolder(mRecyclerView, itemView).setViewType(viewType);

    }


    @Override
    public int getItemViewType(int position) {
        int viewType = TYPE_CHILD;
        if (isHeaderPosition(position)) {
            viewType = TYPE_HEADER;
        } else if (isFooterPosition(position)) {
            viewType = TYPE_FOOTER;
        } else {

            ErvType ervType = ervTypes.get(position);

            if (ervType == null) {
                ervType = getItemErvType(position);
                ervTypes.put(position, ervType);
            }

            viewType = ervType.type;
        }
        return viewType;
    }

    @Override
    public SpRViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        switch (viewType) {

            case TYPE_HEADER:

                return onCreateHeaderViewHolder(parent, viewType);

            case TYPE_FOOTER:

                return onCreateFooterViewHolder(parent, viewType);


            case TYPE_GROUP:
                return onCreateGroupViewHolder(parent, viewType);

        }

        return onCreateChildViewHolder(parent, viewType);

    }

    @Override
    public void onBindViewHolder(SpRViewHolder viewHolder, int position) {

        SpHolderHelper mHolderHelper = viewHolder.getSpHolderHelper();
        mHolderHelper.setPosition(position);

        int viewType = viewHolder.getViewType();

        switch (viewType) {

            case TYPE_HEADER:

                setHeaderView(mHolderHelper, position, header);
                break;

            case TYPE_FOOTER:
                setFooterView(mHolderHelper, position, footer);

                break;

            case TYPE_GROUP:
            case TYPE_CHILD:

                ErvType ervType = ervTypes.get(position);

                if (ervType == null) {
                    ervType = getItemErvType(position);
                    ervTypes.put(position, ervType);
                }


                if (ervType.type == TYPE_CHILD) {


                    setChildView(mHolderHelper, ervType.group, ervType.position, getChildItem(ervType.group, ervType.position));


                } else {

                    setGroupView(mHolderHelper, ervType.group, ervType.position, getGroupItem(ervType.group));


                }


                break;


        }


    }


    public boolean isGroupPosition(int i) {

        ErvType ervType = ervTypes.get(i);

        if (ervType == null) {
            ervType = getItemErvType(i);
            ervTypes.put(i, ervType);
        }


        return ervType.type == TYPE_GROUP;
    }

    public boolean isHeaderPosition(int position) {
        return header != null && position == 0;
    }


    public boolean isFooterPosition(int position) {
        int lastPosition = getItemCount() - 1;
        return footer != null && position == lastPosition;
    }


    public int getSpanIndex(int position, int spanCount) {

        ErvType ervType = ervTypes.get(position);

        if (ervType == null) {
            ervType = getItemErvType(position);
            ervTypes.put(position, ervType);
        }

        if (ervType.type == TYPE_CHILD) {

            return ervType.position % spanCount;
        }

        return 0;

    }

    @Override
    public int getSpanSize(int position) {
        return 1;
    }

    protected abstract void setChildView(SpHolderHelper helper, int group, int position, C bean);

    protected abstract void setGroupView(SpHolderHelper helper, int group, int position, G bean);

    protected abstract void setHeaderView(SpHolderHelper helper, int position, H bean);

    protected abstract void setFooterView(SpHolderHelper helper, int position, F bean);


    @Override
    public void onViewRecycled(SpRViewHolder holder) {


        int viewType = holder.getViewType();

        switch (viewType) {

            case TYPE_HEADER:

                onHeaderViewRecycled(holder);

                break;

            case TYPE_FOOTER:
                onFooterViewRecycled(holder);

                break;

            case TYPE_GROUP:
                onGroupViewRecycled(holder);
                break;
            case TYPE_CHILD:


                onChildViewRecycled(holder);

                break;


        }
    }

    protected void onHeaderViewRecycled(SpRViewHolder holder) {
    }


    protected void onFooterViewRecycled(SpRViewHolder holder) {
    }

    protected void onChildViewRecycled(SpRViewHolder holder) {
    }


    protected void onGroupViewRecycled(SpRViewHolder holder) {
    }


    /**
     * 计算正确的group和position
     *
     * @param i position
     * @return 一个计算好的group和position
     */
    public ErvType getItemErvType(int i) {


        if (header != null) {

            i -= 1;
        }

        for (int group = 0; group < getGroupItemCount(); group++) {


            if (i >= 1) {

                i -= 1;

                if (i < getChildItemCount(group)) {


                    return new ErvType(TYPE_CHILD, group, i);
                }

                i -= getChildItemCount(group);
                continue;

            }

            if (i < 1) {

                return new ErvType(TYPE_GROUP, group, i);
            }


        }


        return new ErvType(TYPE_CHILD, 0, i);
    }


    public static class ErvType {

        public int type;
        public int group;
        public int position;


        public ErvType(int type, int group, int position) {
            this.type = type;
            this.group = group;
            this.position = position;
        }
    }
}
