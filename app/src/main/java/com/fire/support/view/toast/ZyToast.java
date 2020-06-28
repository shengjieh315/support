package com.fire.support.view.toast;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fire.support.R;

import java.lang.reflect.Field;

import androidx.annotation.NonNull;

public class ZyToast {

    private static Toast lastToast;
    private Toast mToast;
    private CharSequence text;

    private ZyToast(Context context, CharSequence text, int duration) {
        this.text = text;
        context = context.getApplicationContext();
        mToast = new Toast(context);
        mToast.setDuration(duration);
        try {
            View v = LayoutInflater.from(context).inflate(R.layout.view_toast, null);

            TextView textView = v.findViewById(R.id.tv_toast);
            textView.setText(text);
            mToast.setView(v);
        } catch (Throwable e) {
            e.printStackTrace();
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        }


    }

    private static void setContextCompat(@NonNull View view, @NonNull Context context) {
        if (Build.VERSION.SDK_INT == 25) {
            try {
                Field field = View.class.getDeclaredField("mContext");
                field.setAccessible(true);
                field.set(view, context);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    public static ZyToast makeText(Context context, CharSequence text, int duration) {

        return new ZyToast(context, text, duration);
    }

    public void show() {
        try {
            if (TextUtils.isEmpty(text)) {
                return;
            }
            if (lastToast != null) {
                lastToast.cancel();
                lastToast = null;
            }
            if (mToast != null) {
                hookToast(mToast);
                mToast.show();
                lastToast = mToast;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    public void setGravity(int gravity, int xOffset, int yOffset) {
        if (mToast != null) {
            mToast.setGravity(gravity, xOffset, yOffset);
        }
    }

    public static void hookToast(Toast toast) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {
            Class<Toast> cToast = Toast.class;
            try {
                //TN是private的
                Field fTn = cToast.getDeclaredField("mTN");
                fTn.setAccessible(true);

                //获取tn对象
                Object oTn = fTn.get(toast);
                //获取TN的class，也可以直接通过Field.getType()获取。
                Class<?> cTn = oTn.getClass();
                Field fHandle = cTn.getDeclaredField("mHandler");

                //重新set->mHandler
                fHandle.setAccessible(true);
                fHandle.set(oTn, new HandlerProxy((Handler) fHandle.get(oTn)));
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

    }
}
