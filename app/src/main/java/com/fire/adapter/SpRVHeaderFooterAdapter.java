package com.fire.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public abstract class SpRVHeaderFooterAdapter<C, H, F> extends RecyclerView.Adapter<SpRViewHolder> implements SpSpanSize {


    public static final int TYPE_CHILD = 0;
    public static final int TYPE_HEADER = 1;
    public static final int TYPE_FOOTER = 2;


    protected int itemChildLayoutId;
    protected int itemHeaderLayoutId;
    protected int itemFooterLayoutId;

    protected Context mContext;

    protected List<C> mChildList;


    protected F footer;
    protected H header;
    protected RecyclerView mRecyclerView;

    //    item项等分个数
    protected int ratio;

    public SpRVHeaderFooterAdapter(RecyclerView mRecyclerView) {
        super();
        this.mContext = mRecyclerView.getContext();
        this.mRecyclerView = mRecyclerView;

        this.mChildList = new ArrayList<>();


    }


    public SpRVHeaderFooterAdapter(RecyclerView mRecyclerView, int itemChildLayoutId, int itemHeaderLayoutId, int itemFooterLayoutId) {
        this(mRecyclerView);
        this.itemChildLayoutId = itemChildLayoutId;

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


    /**
     * child的实际个数
     *
     * @return
     */
    public int getChildItemCount() {

        if (mChildList.size() <= 0) {
            return 0;
        }
        return mChildList.size();
    }


    @Override
    public int getItemCount() {

        int count = 0;

        int headerCount = header == null ? 0 : 1;
        int footerCount = footer == null ? 0 : 1;


        count = headerCount + footerCount + getChildItemCount();


        return count;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    public C getChildItem(int position) {

        position = getChildPosition(position);

        if (isSafePosition(position)) {
            return mChildList.get(position);
        }
        return null;
    }


    public C getItem(int position) {

        if (isHeaderPosition(position)) {
            try {
                Class entityClass = getEntityClass();
                if (header.getClass() == entityClass) {
                    return (C) header;
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        } else if (isFooterPosition(position)) {
            try {
                Class entityClass = getEntityClass();
                if (footer.getClass() == entityClass) {
                    return (C) footer;
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        } else {
            position = getChildPosition(position);

            if (isSafePosition(position)) {
                return mChildList.get(position);
            }
        }


        return null;
    }

    private Class getEntityClass() {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        return (Class) params[0];
    }

    public int getChildPosition(int position) {
        if (header != null) {

            position -= 1;
        }
        return position;
    }


    public List<C> getChildList() {
        return mChildList;
    }


    /**
     * 设置数据
     *
     * @param childData
     */
    public void setList(List<C> childData) {


        mChildList.clear();

        if (childData != null && !childData.isEmpty()) {
            mChildList.addAll(childData);
        }

        notifyDataSetChanged();
    }


    protected SpRViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(mContext).inflate(itemChildLayoutId, parent, false);

        return new SpRViewHolder(mRecyclerView, itemView).setViewType(viewType);

    }


    protected SpRViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(mContext).inflate(itemHeaderLayoutId, parent, false);

        return new SpRViewHolder(mRecyclerView, itemView, ratio).setViewType(viewType);

    }

    protected SpRViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(mContext).inflate(itemFooterLayoutId, parent, false);

        return new SpRViewHolder(mRecyclerView, itemView, ratio).setViewType(viewType);

    }


    @Override
    public int getItemViewType(int position) {
        int viewType = TYPE_CHILD;
        if (isHeaderPosition(position)) {
            viewType = TYPE_HEADER;
        } else if (isFooterPosition(position)) {
            viewType = TYPE_FOOTER;
        } else {
            viewType = TYPE_CHILD;
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


            case TYPE_CHILD:

                setChildView(mHolderHelper, getChildPosition(position), getChildItem(position));


                break;


        }


    }


    public boolean isHeaderPosition(int position) {
        return header != null && position == 0;
    }


    public boolean isFooterPosition(int position) {
        int lastPosition = getItemCount() - 1;
        return footer != null && position == lastPosition;
    }


    protected abstract void setChildView(SpHolderHelper helper, int position, C bean);


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


    @Override
    public int getSpanIndex(int position, int spanCount) {

        if (isHeaderPosition(position) || isFooterPosition(position)) {
            return 0;
        }

        return getChildPosition(position) % spanCount;
    }

    @Override
    public int getSpanSize(int position) {
        return 1;
    }

    @Override
    public boolean isGroupPosition(int position) {
        return false;
    }


    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    /**
     * 获取数据
     *
     * @return List
     */
    public List<C> getList() {
        return mChildList;
    }


    /**
     * 获取一个从copy的list
     *
     * @return List
     */
    public List<C> getCopyList() {

        List<C> list = new ArrayList<>();
        list.addAll(mChildList);
        return list;
    }


    /**
     * 添加到头部
     *
     * @param datas List
     */
    public void addNewList(List<C> datas) {
        if (datas != null && !datas.isEmpty()) {
            mChildList.addAll(0, datas);
            notifyDataSetChanged();
        }
    }

    /**
     * 添加到末尾
     *
     * @param datas List
     */
    public void addMoreList(List<C> datas) {
        if (datas != null && !datas.isEmpty()) {
            mChildList.addAll(datas);
            notifyDataSetChanged();
        }
    }


    /**
     * 清空
     */
    public void clear() {
        mChildList.clear();
        notifyDataSetChanged();
    }


    /**
     * 删除指定索引数据条目
     *
     * @param position position
     */
    public void removeItem(int position) {
        if (isSafePosition(position)) {
            mChildList.remove(position);
            notifyItemRangeRemoved(position, 1);
        }

    }

    /**
     * 删除指定数据条目
     *
     * @param model T
     */
    public void removeItem(C model) {

        if (mChildList != null && mChildList.contains(model)) {
            int index = mChildList.indexOf(model);
            if (isSafePosition(index)) {
                removeItem(index);
            }
        }


    }

    /**
     * 删除一个列表
     *
     * @param datas ListC
     */
    public void removeList(List<C> datas) {
        mChildList.removeAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 在指定位置添加数据条目
     *
     * @param position int
     * @param model    T
     */
    public void addItem(int position, C model) {

        if (position >= 0 && position <= mChildList.size()) {
            mChildList.add(position, model);
            notifyItemInserted(position);
        }


    }

    /**
     * 在集合头部添加数据条目
     *
     * @param model T
     */
    public void addFirstItem(C model) {
        addItem(0, model);
    }

    /**
     * 在集合末尾添加数据条目
     *
     * @param model T
     */
    public void addLastItem(C model) {
        addItem(mChildList.size(), model);
    }

    /**
     * 替换指定索引的数据条目
     *
     * @param location int
     * @param newModel T
     */
    public void setItem(int location, C newModel) {

        if (isSafePosition(location)) {
            mChildList.set(location, newModel);

            notifyItemChanged(location);
        }

    }

    /**
     * 替换指定数据条目
     *
     * @param oldModel T
     * @param newModel T
     */
    public void setItem(C oldModel, C newModel) {
        setItem(mChildList.indexOf(oldModel), newModel);
    }

    /**
     * 交换两个数据条目的位置
     *
     * @param fromPosition int
     * @param toPosition   int
     */
    public void moveItem(int fromPosition, int toPosition) {

        if (isSafePosition(fromPosition) && isSafePosition(toPosition)) {
            Collections.swap(mChildList, fromPosition, toPosition);
            notifyItemMoved(fromPosition, toPosition);
        }

    }

    private boolean isSafePosition(int position) {


        return mChildList != null && position >= 0 && position < mChildList.size();

    }
}
