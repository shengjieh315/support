package com.fire.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class SpAdapter<T> extends BaseAdapter {

    private Context mContext;
    private List<T> mList;
    private int mItemLayoutId;
    protected SpOnItemListener mOnItemListener;

    public SpAdapter(Context context) {
        this.mContext = context;
        mList = new ArrayList<>();
    }

    public SpAdapter(Context context, int itemLayoutId) {
        this(context);
        mItemLayoutId = itemLayoutId;
    }

    public SpAdapter(Context context, int itemLayoutId, List<T> mList) {
        this(context, itemLayoutId);
        if (mList != null && !mList.isEmpty()) {
            this.mList.addAll(mList);
        }
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SpHolderHelper helper = SpHolderHelper.holderHelperByConvertView(convertView, parent, mItemLayoutId);
        helper.setOnItemListener(mOnItemListener);
        helper.setPosition(position);
        setItemListener(helper);
        setView(helper, position, getItem(position));
        return helper.getConvertView();
    }

    /**
     * 获取数据
     *
     * @return
     */
    public List<T> getList() {
        return mList;
    }

    /**
     * 添加到头部
     *
     * @param list
     */
    public void addNewList(List<T> list) {
        if (list != null && !list.isEmpty()) {
            mList.addAll(0, list);
            notifyDataSetChanged();
        }
    }

    /**
     * 添加到末尾
     *
     * @param list
     */
    public void addMoreList(List<T> list) {
        if (list != null && !list.isEmpty()) {
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    /**
     * 设置数据
     *
     * @param list
     */
    public void setList(List<T> list) {
        mList.clear();
        if (list != null && !list.isEmpty()) {
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }

    /**
     * 设置item中的子控件点击事件监听器
     *
     * @param onItemListener
     */
    public void setOnItemListener(SpOnItemListener onItemListener) {
        mOnItemListener = onItemListener;
    }

    protected abstract void setView(SpHolderHelper helper, int position, T bean);

    protected abstract void setItemListener(SpHolderHelper helper);

}
