package com.fire.support.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.fire.support.R;
import com.fire.support.helper.PhoneHelper;
import com.fire.support.utils.Utils;
import com.fire.support.utils.screen.ScreenAdaptUtil;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    //   上下文
    public BaseActivity context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_Main);
        context = this;
        try {
            ScreenAdaptUtil.setCustomDensity(context, getApplication());
            if (Utils.isMaxLOLLIPOP()) {
                View rootView = findViewById(android.R.id.content);
                rootView.setPadding(0, getStatusBarHeight(), 0, 0);
            }
            initView(savedInstanceState);
            initListener(savedInstanceState);
            initData(savedInstanceState);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public abstract void initView(Bundle savedInstanceState);

    public abstract void initListener(Bundle savedInstanceState);

    public abstract void initData(Bundle savedInstanceState);

    /**
     * 返回按钮
     *
     * @param item MenuItem
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * 得到根view
     *
     * @return View
     */
    public View getRootView() {
        return ((ViewGroup) context.findViewById(android.R.id.content))
                .getChildAt(0);
    }

    /**
     * 隐藏键盘
     *
     * @param et EditText
     */
    public void hideKeyBoard(EditText et) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    /**
     * 显示键盘
     *
     * @param et EditText
     */
    public void showKeyBoard(EditText et) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInputFromInputMethod(et.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    /**
     * activity的高
     *
     * @return int
     */
    public int getScreenHeight() {
        return findViewById(android.R.id.content).getHeight();
    }

    /**
     * activity的高
     *
     * @return int
     */
    public int getScreenWidth() {
        return findViewById(android.R.id.content).getWidth();
    }

    /**
     * 状态栏高度
     *
     * @return int
     */
    public int getStatusBarHeight() {

        return PhoneHelper.getInstance().getStatusBarHeight();

    }

    /**
     * ActionBar的高
     *
     * @return int
     */
    public int getActionBarSize() {
        TypedValue typedValue = new TypedValue();
        int[] textSizeAttr = new int[]{R.attr.actionBarSize};
        int indexOfAttrTextSize = 0;
        TypedArray a = obtainStyledAttributes(typedValue.data, textSizeAttr);
        int actionBarSize = a.getDimensionPixelSize(indexOfAttrTextSize, -1);
        a.recycle();
        return actionBarSize;
    }

    /**
     * 返回上一个页面
     */
    @Override
    public void onBackPressed() {
        try {
            super.onBackPressed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除掉super.onSaveInstanceState 避免置于后台后切换回来引起的问题
     *
     * @param outState Bundle
     */
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {

    }


}
