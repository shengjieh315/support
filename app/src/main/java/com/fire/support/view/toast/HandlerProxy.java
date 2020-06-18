package com.fire.support.view.toast;

import android.os.Handler;
import android.os.Message;

public class HandlerProxy extends Handler {

    private Handler mHandler;

    public HandlerProxy(Handler handler) {
        this.mHandler = handler;
    }

    @Override
    public void handleMessage(Message msg) {
        try {
            mHandler.handleMessage(msg);
        } catch (Throwable e) {
            //ignore
        }
    }
}
