package com.fire.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.StringRes;

public class SpHolderHelper implements View.OnClickListener, View.OnLongClickListener {

    protected final SparseArray<View> mViews;
    protected View mConvertView;
    protected Context mContext;
    public int mPosition;
    protected SpOnItemListener mOnItemListener;
    protected boolean isRV;//是RecyclerView

    public SpHolderHelper(View convertView) {
        this.isRV = true;
        this.mViews = new SparseArray<>();
        this.mConvertView = convertView;
        this.mContext = convertView.getContext();
        this.mConvertView.setTag(this);
    }

    public SpHolderHelper(ViewGroup parent, int layoutId) {
        this.mViews = new SparseArray<>();
        this.mConvertView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        this.mContext = parent.getContext();
        this.mConvertView.setTag(this);
    }

    public static SpHolderHelper holderHelperByConvertView(View convertView, ViewGroup parent, int layoutId) {
        if (convertView == null) {
            return new SpHolderHelper(parent, layoutId);
        }
        return (SpHolderHelper) convertView.getTag();
    }

    public static SpHolderHelper holderHelperByRecyclerView(View convertView) {
        return new SpHolderHelper(convertView);
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    /**
     * 设置item中的子控件点击事件监听器
     *
     * @param onItemListener
     */
    public void setOnItemListener(SpOnItemListener onItemListener) {
        mOnItemListener = onItemListener;
    }

    /**
     * 为id设置点击事件
     *
     * @param viewId
     */
    public void setItemChildClickListener(@IdRes int viewId) {
        getView(viewId).setOnClickListener(this);
    }

    /**
     * 为id设置长按事件
     *
     * @param viewId
     */
    public void setItemChildLongClickListener(@IdRes int viewId) {
        getView(viewId).setOnLongClickListener(this);
    }

    /**
     * 通过控件的Id获取对应的控件，如果没有则加入mViews，则从item根控件中查找并保存到mViews中
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(@IdRes int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 通过ImageView的Id获取ImageView
     *
     * @param viewId
     * @return
     */
    public ImageView getImageView(@IdRes int viewId) {
        return getView(viewId);
    }

    /**
     * 通过TextView的Id获取TextView
     *
     * @param viewId
     * @return
     */
    public TextView getTextView(@IdRes int viewId) {
        return getView(viewId);
    }

    /**
     * 获取item的根控件
     *
     * @return
     */
    public View getConvertView() {
        return mConvertView;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemListener != null) {
            mOnItemListener.onItemChildClick(v, mPosition);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mOnItemListener != null) {
            return mOnItemListener.onItemChildLongClick(v, mPosition);
        }
        return false;
    }

    /**
     * 设置对应id的控件的文本内容
     *
     * @param viewId
     * @param text
     * @return
     */
    public SpHolderHelper setText(@IdRes int viewId, CharSequence text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * 设置对应id的控件的文本内容
     *
     * @param viewId
     * @param stringResId 字符串资源id
     * @return
     */
    public SpHolderHelper setText(@IdRes int viewId, @StringRes int stringResId) {
        TextView view = getView(viewId);
        view.setText(stringResId);
        return this;
    }

    /**
     * 设置对应id的控件的html文本内容
     *
     * @param viewId
     * @param source html文本
     * @return
     */
    public SpHolderHelper setHtml(@IdRes int viewId, String source) {
        TextView view = getView(viewId);
        view.setText(Html.fromHtml(source));
        return this;
    }

    /**
     * 设置对应id的控件是否选中
     *
     * @param viewId
     * @param checked
     * @return
     */
    public SpHolderHelper setChecked(@IdRes int viewId, boolean checked) {
        Checkable view = getView(viewId);
        view.setChecked(checked);
        return this;
    }

    public SpHolderHelper setTag(@IdRes int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    public SpHolderHelper setTag(@IdRes int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public SpHolderHelper setVisibility(@IdRes int viewId, int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
        return this;
    }

    public SpHolderHelper setImageBitmap(@IdRes int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public SpHolderHelper setImageDrawable(@IdRes int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    /**
     * @param viewId
     * @param textColorResId 颜色资源id
     * @return
     */
    public SpHolderHelper setTextColorRes(@IdRes int viewId, @ColorRes int textColorResId) {
        TextView view = getView(viewId);
        view.setTextColor(mContext.getResources().getColor(textColorResId));
        return this;
    }

    /**
     * @param viewId
     * @param textColor 颜色值
     * @return
     */
    public SpHolderHelper setTextColor(@IdRes int viewId, int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    /**
     * @param viewId
     * @param backgroundResId 背景资源id
     * @return
     */
    public SpHolderHelper setBackgroundRes(@IdRes int viewId, int backgroundResId) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundResId);
        return this;
    }

    /**
     * @param viewId
     * @param color  颜色值
     * @return
     */
    public SpHolderHelper setBackgroundColor(@IdRes int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    /**
     * @param viewId
     * @param colorResId 颜色值资源id
     * @return
     */
    public SpHolderHelper setBackgroundColorRes(@IdRes int viewId, @ColorRes int colorResId) {
        View view = getView(viewId);
        view.setBackgroundColor(mContext.getResources().getColor(colorResId));
        return this;
    }

    /**
     * @param viewId
     * @param imageResId 图像资源id
     * @return
     */
    public SpHolderHelper setImageResource(@IdRes int viewId, @DrawableRes int imageResId) {
        ImageView view = getView(viewId);
        view.setImageResource(imageResId);
        return this;
    }


}
