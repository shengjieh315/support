package com.fire.okhttp.threadpool;

public interface FutureListener<T> {
    void onFutureDone(T future);
}
