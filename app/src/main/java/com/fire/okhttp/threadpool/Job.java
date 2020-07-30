package com.fire.okhttp.threadpool;


public interface Job<T> {
    T run();
}
